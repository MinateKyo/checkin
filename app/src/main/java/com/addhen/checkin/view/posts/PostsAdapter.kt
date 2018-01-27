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

package com.addhen.checkin.view.posts

import android.content.Context
import android.view.ViewGroup
import com.addhen.checkin.R
import com.addhen.checkin.databinding.PostItemBinding
import com.addhen.checkin.util.RxScheduler
import com.hellofresh.barcodescanner.presentation.view.base.BaseBindingHolder
import com.hellofresh.barcodescanner.presentation.view.base.BaseRecyclerAdapter

class PostsAdapter(
    context: Context,
    rxScheduler: RxScheduler) : BaseRecyclerAdapter<PostItemViewModel, BaseBindingHolder<PostItemBinding>>(context, rxScheduler) {

  override fun areItemsTheSame(oldItem: PostItemViewModel, newItem: PostItemViewModel): Boolean {
    return oldItem.post.id == newItem.post.id
  }

  override fun areContentsTheSame(oldItem: PostItemViewModel, newItem: PostItemViewModel): Boolean {
    return oldItem.post == newItem.post
  }

  override fun onCreateViewHolder(
      parent: ViewGroup,
      viewType: Int): BaseBindingHolder<PostItemBinding> {
    return BaseBindingHolder(context, parent, R.layout.post_item)
  }

  override fun onBindViewHolder(holder: BaseBindingHolder<PostItemBinding>, position: Int) {
    val viewModel = getItem(position)
    val itemBinding = holder.binding
    itemBinding.viewModel = viewModel
    itemBinding.executePendingBindings()
  }
}
