package com.example.ontime.dosen

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DosenRepository {

    private val database = FirebaseDatabase.getInstance()
    private val dosenRef = database.getReference("dosen")

    private val _dosens = MutableStateFlow<List<Dosen>>(emptyList())
    val dosens: StateFlow<List<Dosen>> get() = _dosens

    init {
        // Listen to data changes in Firebase
        dosenRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fetchedNotes = snapshot.children.mapNotNull { it.getValue(Dosen::class.java) }
                _dosens.value = fetchedNotes
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }



    fun insertDosenRep(dosen: Dosen) {
        dosen.id = dosenRef.push().key
        dosen.id?.let { dosenRef.child(it).setValue(dosen) }
    }

    fun deleteDosenRep(dosen: Dosen) {
        dosen.id?.let { dosenRef.child(it).removeValue() }
    }
}