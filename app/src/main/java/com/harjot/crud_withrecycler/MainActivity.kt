package com.harjot.crud_withrecycler

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.harjot.crud_withrecycler.databinding.ActivityMainBinding
import com.harjot.crud_withrecycler.databinding.BtnNegativeDialogBinding
import com.harjot.crud_withrecycler.databinding.FabBtnDialogBinding
import java.text.FieldPosition

class MainActivity : AppCompatActivity(),RecyclerInterface {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter:RecyclerAdapterClass
    var arrayList= ArrayList<UserModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        adapter=RecyclerAdapterClass(arrayList,this)
        binding.rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rv.adapter=adapter

        binding.btnFab.setOnClickListener {
            var dialog= Dialog(this)
            var dialogBinding=FabBtnDialogBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialog.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            dialogBinding.btnAdd1.setOnClickListener {
                if(dialogBinding.etItemName.text.toString().trim().isNullOrEmpty()){
                    dialogBinding.etItemName.error= "Enter Name!"
                }
                else{
                    arrayList.add(UserModel(dialogBinding.etItemName.text.toString()))

                    dialog.dismiss()
                    adapter.notifyDataSetChanged()
                }
            }
            dialog.show()
        }

    }
    override fun click(position: Int) {
        var alertDialog=AlertDialog.Builder(this)
        alertDialog.setTitle(resources.getString(R.string.deleteUpdate))
        alertDialog.setMessage(resources.getString(R.string.message))
        alertDialog.setCancelable(false)

        alertDialog.setNeutralButton(resources.getString(R.string.cancel)){_,_->
            alertDialog.setCancelable(true)
        }
        alertDialog.setNegativeButton(resources.getString(R.string.update)){_,_->
            var dialog=Dialog(this)
            var dialogBinding=BtnNegativeDialogBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialog.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )

            dialogBinding.btnUpdate.setOnClickListener {
                if (dialogBinding.etItemName.text.toString().trim().isNullOrEmpty()){
                    dialogBinding.etItemName.error="Enter Name"
                }
                else{
                    arrayList[position]= UserModel(dialogBinding.etItemName.text.toString())
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
            }
            dialog.show()
        }
        alertDialog.setPositiveButton(resources.getString(R.string.delete)){_,_->
            arrayList.removeAt(position)
            adapter.notifyDataSetChanged()
        }
        alertDialog.show()
    }
}