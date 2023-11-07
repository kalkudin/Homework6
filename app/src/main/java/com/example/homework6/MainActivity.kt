package com.example.homework6

import android.graphics.Color
import android.media.MediaRouter.UserRouteInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.homework6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val inputFieldList = listOf(binding.firstName, binding.lastName, binding.email, binding.age)
        val userList = mutableListOf<UserData>()
        var deletedUserCount:Int = 0

        binding.addUser.setOnClickListener(){
            if(checkEmptyFields(inputFieldList)){
                binding.displayText.setText("Please Fill Out All Fields")
                binding.displayText.setTextColor(Color.RED)
                return@setOnClickListener
            }
            if(!checkValidEmail(binding.email.text.toString())){
                binding.displayText.setText("Invalid Email")
                binding.displayText.setTextColor(Color.RED)
                return@setOnClickListener
            }
            val newUser:UserData = UserData(binding.firstName.text.toString(), binding.lastName.text.toString(), binding.email.text.toString(), binding.age.text.toString().toInt())

            if(!checkIfUserExists(userList,newUser)){
                userList.add(newUser)
                binding.displayText.setText("User Added")
                binding.displayText.setTextColor(Color.GREEN)
                binding.userCount.setText("user count: ${userList.size}")
            }
            else{
                binding.displayText.setText("User Already Exists")
                binding.displayText.setTextColor(Color.RED)
            }
        }

        binding.removeUser.setOnClickListener(){
            if(checkEmptyFields(inputFieldList)){
                binding.displayText.setText("Please Fill Out All Fields")
                binding.displayText.setTextColor(Color.RED)
                return@setOnClickListener
            }
            if(!checkValidEmail(binding.email.text.toString())){
                binding.displayText.setText("Invalid Email")
                binding.displayText.setTextColor(Color.RED)
                return@setOnClickListener
            }
            val userToDelete:UserData = UserData(binding.firstName.text.toString(), binding.lastName.text.toString(), binding.email.text.toString(), binding.age.text.toString().toInt())

            if(!checkIfUserExists(userList, userToDelete)){
                binding.displayText.setText("This User Does Not Exist")
                binding.displayText.setTextColor(Color.RED)
                return@setOnClickListener
            }
            userList.remove(userToDelete)
            binding.displayText.setText("User Removed")
            binding.displayText.setTextColor(Color.GREEN)
            binding.userCount.setText("user count: ${userList.size}")
            binding.deletedUserCount.setText("deleted count: ${deletedUserCount + 1}")
        }

        binding.updateUser.setOnClickListener(){
            if(checkEmptyFields(inputFieldList)){
                binding.displayText.setText("Please Fill Out All Fields")
                binding.displayText.setTextColor(Color.RED)
                return@setOnClickListener
            }
            if(!checkValidEmail(binding.email.text.toString())){
                binding.displayText.setText("Invalid Email")
                binding.displayText.setTextColor(Color.RED)
                return@setOnClickListener
            }
            val userToUpdate:UserData = UserData(binding.firstName.text.toString(), binding.lastName.text.toString(), binding.email.text.toString(), binding.age.text.toString().toInt())

            if(!checkIfUserExists(userList, userToUpdate)){
                binding.displayText.setText("This User Does Not Exist")
                binding.displayText.setTextColor(Color.RED)
                return@setOnClickListener
            }
            val matchingUser = userList.find{it.email == userToUpdate.email}
            matchingUser?.let{
                val index = userList.indexOf(matchingUser)
                userList[index] = userToUpdate
                binding.displayText.setText("User Updated")
                binding.displayText.setTextColor(Color.GREEN)
            }
        }
    }

    private fun checkIfUserExists(currentUserList:MutableList<UserData>, newUser:UserData):Boolean{
        return currentUserList.any{it.email == newUser.email}
    }

    private fun checkEmptyFields(inputList:List<EditText>):Boolean{
        return inputList.any{element -> element.text.isNullOrBlank()}
    }

    private fun checkValidEmail(email:String):Boolean{
        return email.trim().contains("@") && !email.trim().startsWith("@") && !email.trim().endsWith("@")
    }

}