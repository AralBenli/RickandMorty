package com.example.rickandmorty.utils

import android.content.Context
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.rickandmorty.R
import com.example.rickandmorty.local.cache_characters.CachedCharacterEntity
import com.example.rickandmorty.local.favorite.FavoriteEntity
import com.example.rickandmorty.response.CharacterItem

object Extensions {

    infix fun ImageView.setImage(url: Int) {
        Glide.with(context)
            .load(url)
            .fitCenter()
            .into(this)
    }

    infix fun ImageView.setImageUrl(url: String) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions().override(1440, 1080))
            .into(this)
    }


    fun <T, R> List<T>.mapToList(transform: (T) -> R): List<R> {
        return this.map(transform)
    }
}

    fun Fragment.findNavControllerSafely(): NavController? {
        return if (isAdded) {
            findNavController()
        } else {
            null
        }
    }

    fun runLayoutAnimation(recyclerView: RecyclerView) {
        val context: Context = recyclerView.context
        val controller: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)

        recyclerView.layoutAnimation = controller
        recyclerView.adapter?.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }

    fun mapToCharacterItem(pagingData: PagingData<CachedCharacterEntity>): PagingData<CharacterItem> {
        return pagingData.map { cachedCharacter ->
            CharacterItem(
                id = cachedCharacter.id,
                name = cachedCharacter.name,
                status = cachedCharacter.status,
                species = cachedCharacter.species,
                type = cachedCharacter.type,
                gender = cachedCharacter.gender,
                origin = cachedCharacter.origin,
                location = cachedCharacter.location,
                image = cachedCharacter.image,
                isFavorite = false
            )
        }
    }

    fun mapFavoriteToCharacterItem(favoriteEntity: FavoriteEntity): CharacterItem {
        return CharacterItem(
            id = favoriteEntity.id,
            name = favoriteEntity.name,
            status = favoriteEntity.status,
            species = favoriteEntity.species,
            type = favoriteEntity.type,
            gender = favoriteEntity.gender,
            origin = favoriteEntity.origin,
            location = favoriteEntity.location,
            image = favoriteEntity.image,
            isFavorite = false

        )
    }

