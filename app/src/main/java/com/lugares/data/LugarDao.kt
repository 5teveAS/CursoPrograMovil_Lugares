package com.lugares.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
//import androidx.room.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
import com.lugares.model.Lugar

//@Dao
class LugarDao {

    private lateinit var codigoUsuario: String
    private lateinit var firestore: FirebaseFirestore

    init {
        val usuario = Firebase.auth.currentUser?.email
        codigoUsuario = "$usuario"
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    //Para obtener la lista de Lugares
    fun getLugares(): MutableLiveData<List<Lugar>>{
        val listaLugares = MutableLiveData<List<Lugar>>()
        firestore.collection("lugaresApp")
            .document(codigoUsuario)
            .collection("misLugares")
            .addSnapshotListener{ snapshot, e->
                if (e != null){
                    return@addSnapshotListener
            }
                if(snapshot != null){
                    val lista = ArrayList<Lugar>()
                    val lugares = snapshot.documents
                    lugares.forEach{
                        val lugar = it.toObject(Lugar::class.java)
                        if(lugar != null){
                            lista.add(lugar)
                        }
                    }
                    listaLugares.value = lista
                }

            }
        return listaLugares
    }

    // Salvar Lugar
    fun saveLugar(lugar: Lugar){
        val document: DocumentReference
        if(lugar.id.isEmpty()){
            document = firestore
                .collection("lugaresApp")
                .document(codigoUsuario)
                .collection("misLugares")
                .document()
                lugar.id = document.id
        } else {
            document = firestore
                .collection("lugaresApp")
                .document(codigoUsuario)
                .collection("misLugares")
                .document(lugar.id)
        }
        val set = document.set(lugar)
        set.addOnSuccessListener {
            Log.d("Add Lugar", "Lugar Agregado")
        }
            .addOnCanceledListener {
                Log.e("Add Lugar", "Lugar NO Agregado")
            }
    }

    fun deleteLugar(lugar:Lugar){
        if(lugar.id.isNotEmpty()){
            firestore
            .collection("LugaresApp")
                .document(codigoUsuario)
                .collection("misLugares")
                .document(lugar.id)
                .delete()
                .addOnSuccessListener {
                    Log.d("Add Lugar", "Lugar eliminado")

                }.addOnCanceledListener {
                    Log.e("Add Lugar", "Lugar NO eliminado")
                }
        }
    }

    //Función para obtener la lista de lugares
    //@Query("select * from LUGAR")
    //fun getAllData() : LiveData<List<Lugar>>

    //@Insert(onConflict = OnConflictStrategy.IGNORE)
   // suspend fun addLugar(lugar: Lugar)

    //@Update(onConflict = OnConflictStrategy.IGNORE)
    //suspend fun updateLugar(lugar: Lugar)

    //@Delete
    //suspend fun deleteLugar(lugar: Lugar)

}