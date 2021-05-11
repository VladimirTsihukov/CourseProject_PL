package com.adnroidapp.muvieapp.model.cache.roomcache

import android.util.Log
import com.adnroidapp.muvieapp.model.ClassKey
import com.adnroidapp.muvieapp.model.ClassKey.SEPARATOR
import com.adnroidapp.muvieapp.model.api.data.Cast
import com.adnroidapp.muvieapp.model.api.data.MoviesDetail
import com.adnroidapp.muvieapp.model.cache.IMoviesDetailCache
import com.adnroidapp.muvieapp.model.entity.room.data.RoomDetailMovie
import com.adnroidapp.muvieapp.model.entity.room.db.DBMovies
import io.reactivex.Single

class CacheRoomMovieDetail(val db: DBMovies) : IMoviesDetailCache {

    override fun getMoviesDetailForID(id: Long): Single<RoomDetailMovie> = Single.fromCallable {
        db.moviesDetail().getMovieFromID(id)
    }

    override fun putMoviesDetail(movies: MoviesDetail) {
        Log.v(ClassKey.LOG_KEY, "CacheRoomMovieDetail putMoviesDetail(movies.size = ${movies.title})")
        val movieRoom = movies.let { movie ->
            RoomDetailMovie(
                id = movie.id,
                title = movie.title,
                overview = movie.overview,
                backdrop = movie.backdropPath,
                ratings = movie.ratings,
                numberOfRatings = movie.voteCount.toInt(),
                minimumAge = if (movie.adult) 16 else 13,
                runtime = movie.runtime,
                genres = movie.genres.joinToString (", "){ it.name },
            )
        }
        db.moviesDetail().insertMovieDetail(movieRoom)
    }

    override fun getMovieActors(id: Long): Single<List<Cast>> = Single.fromCallable {
        val listCast = mutableListOf<Cast>()
        val nameActorsList = db.moviesDetail().getNameActor(id).split(SEPARATOR)
        val profilePaths = db.moviesDetail().getProfilePaths(id).split(SEPARATOR)
        nameActorsList.forEachIndexed { index, _ ->
            listCast.add(Cast(name = nameActorsList[index], profilePath = profilePaths[index]))
        }
        listCast
    }

    override fun putMoviesActors(id: Long, listCast: List<Cast>) {
        db.moviesDetail().setNameActor(listCast.joinToString(SEPARATOR){it.name}, id)
        db.moviesDetail().setProfilePaths(listCast.joinToString(SEPARATOR) {it.profilePath ?: " "}, id)
    }
}