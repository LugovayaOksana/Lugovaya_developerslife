package com.example.developerslife.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.developerslife.data.data_source.PostDataBase
import com.example.developerslife.data.remote.PostApi
import com.example.developerslife.data.remote.dto.toFeedPost
import com.example.developerslife.domain.model.FeedPost
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class FeedPageRemoteMediator(
    private val api: PostApi,
    private val db: PostDataBase,
    private val section: String,
    private val pageSize: Int
): RemoteMediator<Int, FeedPost>() {

    private val dao = db.feedPostDao

    override suspend fun initialize(): InitializeAction = InitializeAction.SKIP_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FeedPost>
    ): MediatorResult {
        try {
            val lastIndexInDB = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> db.withTransaction {
                    dao.getNextIndexInSection(section)
                }
            }

            val data = api.getPostsPage(
                section = section,
                page = lastIndexInDB / pageSize,
            )

            val items = data.items.mapIndexed{ index, item ->
                item.toFeedPost(section, lastIndexInDB + index)
            }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dao.deleteBySection(section)
                }

                dao.insert(items)
            }

            return MediatorResult.Success(endOfPaginationReached = items.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}