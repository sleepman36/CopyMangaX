
package com.crow.module_home.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition
import com.bumptech.glide.request.transition.NoTransition
import com.crow.mangax.copymanga.appComicCardHeight
import com.crow.mangax.copymanga.appComicCardWidth
import com.crow.mangax.copymanga.appDp10
import com.crow.mangax.copymanga.entity.IBookAdapterColor
import com.crow.mangax.copymanga.formatValue
import com.crow.mangax.copymanga.glide.AppGlideProgressFactory
import com.crow.base.tools.extensions.BASE_ANIM_200L
import com.crow.base.tools.extensions.doOnClickInterval
import com.crow.mangax.ui.adapter.BaseGlideLoadingViewHolder
import com.crow.module_home.databinding.HomeFragmentSearchRvBinding
import com.crow.module_home.model.resp.search.novel_result.SearchNovelResult

class SearchNovelRvAdapter(
    inline val doOnTap: (SearchNovelResult) -> Unit
) : PagingDataAdapter<SearchNovelResult, SearchNovelRvAdapter.LoadingViewHolder>(DiffCallback()), IBookAdapterColor<SearchNovelRvAdapter.LoadingViewHolder> {

    class DiffCallback: DiffUtil.ItemCallback<SearchNovelResult>() {
        override fun areItemsTheSame(oldItem: SearchNovelResult, newItem: SearchNovelResult): Boolean {
            return oldItem.mPathWord == newItem.mPathWord
        }

        override fun areContentsTheSame(oldItem: SearchNovelResult, newItem: SearchNovelResult): Boolean {
            return oldItem == newItem
        }
    }

    inner class LoadingViewHolder(binding: HomeFragmentSearchRvBinding) : BaseGlideLoadingViewHolder<HomeFragmentSearchRvBinding>(binding)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadingViewHolder {
        return LoadingViewHolder(HomeFragmentSearchRvBinding.inflate(LayoutInflater.from(parent.context), parent,false)).also { vh ->

            val layoutParams = vh.binding.homeSearchRvImage.layoutParams
            layoutParams.width = appComicCardWidth - appDp10
            layoutParams.height = appComicCardHeight

            vh.binding.homeSearchRvImage.doOnClickInterval {
                doOnTap(getItem(vh.absoluteAdapterPosition) ?: return@doOnClickInterval)
            }
        }
    }

    override fun onBindViewHolder(vh: LoadingViewHolder, position: Int) {
        val item = getItem(position) ?: return

        vh.binding.homeSearchRvLoading.isVisible = true
        vh.binding.homeSearchRvProgressText.isVisible = true
        vh.binding.homeSearchRvProgressText.text = AppGlideProgressFactory.PERCENT_0
        vh.mAppGlideProgressFactory?.onRemoveListener()?.onCleanCache()
        vh.mAppGlideProgressFactory = AppGlideProgressFactory.createGlideProgressListener(item.mImageUrl) { _, _, percentage, _, _ ->
            vh.binding.homeSearchRvProgressText.text = AppGlideProgressFactory.getProgressString(percentage)
        }

        Glide.with(vh.itemView.context)
            .load(item.mImageUrl)
            .listener(vh.mAppGlideProgressFactory?.getRequestListener())
            .transition(GenericTransitionOptions<Drawable>().transition { dataSource, _ ->
                if (dataSource == DataSource.REMOTE) {
                    vh.binding.homeSearchRvLoading.isInvisible = true
                    vh.binding.homeSearchRvProgressText.isInvisible = true
                    DrawableCrossFadeTransition(BASE_ANIM_200L.toInt(), true)
                } else {
                    vh.binding.homeSearchRvLoading.isInvisible = true
                    vh.binding.homeSearchRvProgressText.isInvisible = true
                    NoTransition()
                }
            })
            .into(vh.binding.homeSearchRvImage)
        vh.binding.homeSearchRvName.text = item.mName
        vh.binding.homeSearchRvAuthor.text = item.mAuthor.joinToString { it.mName }
        vh.binding.homeSearchRvHot.text = formatValue(item.mPopular)
    }

    override fun setColor(vh: LoadingViewHolder, color: Int) {
        vh.binding.homeSearchRvName.setTextColor(color)
        vh.binding.homeSearchRvAuthor.setTextColor(color)
        vh.binding.homeSearchRvHot.setTextColor(color)
    }
}