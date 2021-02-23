package com.adnroidapp.muvieapp.mvp.model.entity.dao

import androidx.room.*
import com.adnroidapp.muvieapp.mvp.model.entity.room.data.RoomLikeMovie

@Dao
interface DaoMoviesLike {

    @Query("SELECT * FROM tableMoviesLike ORDER BY voteAverage")
    fun getMoviesLike(): List<RoomLikeMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insetMoviesLike(moviesLike: RoomLikeMovie)

    @Query("DELETE FROM tableMoviesLike WHERE id = :id")
    fun deleteMoviesLike(id: Long)

    @Query("SELECT id FROM tableMoviesLike")
    fun getAllID() : List<Long>
}