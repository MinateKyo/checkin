/*
 * Copyright (c) 2015 - 2018 Henry Addo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.addhen.checkin.data.repository.post

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.addhen.checkin.data.room.entity.Post
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostDataRepository @Inject constructor(val local: LocalDataSource) : PostRepository {

  suspend fun getPosts() = getPosts(10, 0)

  override fun getPosts(limit: Int, page: Int): LiveData<List<Post>> {
    return local.getPosts(limit, page)
  }

  @WorkerThread
  override suspend fun getPost(id: Long): Post {
    return local.getPost(id)
  }
}