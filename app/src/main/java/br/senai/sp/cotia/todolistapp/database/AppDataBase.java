package br.senai.sp.cotia.todolistapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.senai.sp.cotia.todolistapp.model.Tarefa;


    @Database(entities = {Tarefa.class}, version = 1 )
    public abstract class AppDataBase extends RoomDatabase{
        //variavel para acessar a dataBase
        private static  AppDataBase dataBase;
        //metodo para tarefa dao
        public abstract TarefaDao getTarefaDao();

        //metodo para instaciar o appDatabase
        public  static AppDataBase getDataBase(Context context){
            //verifico se ja existe, e se a database eé nula
            if(dataBase == null){
                //instanciado a databse
                dataBase = Room.databaseBuilder
                        (context.getApplicationContext(),AppDataBase.class, "todolist").build();
            }
            return dataBase;
        }
    }
