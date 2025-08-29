package com.example.cadastrofirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebase.ui.theme.FirebaseTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirebaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    app()
                }
            }
        }
    }
}


data class Usuario(
    val nome: String = "",
    val email: String = "",
    val telefone: String = "",
    val senha: String = "",
    val mensagem: String = ""
)

@Composable
fun app(){
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var mensagem by remember { mutableStateOf("") }
    val db = Firebase.firestore
    var usuarios by remember { mutableStateOf<List<Usuario>>(emptyList()) }

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        Arrangement.Center

    ) {
        Box(
            modifier = Modifier
                .height(120.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF6200EE),
                            Color(0xFFBB86FC)
                        )
                    )
                )
                .fillMaxWidth()
        )
        {


        }
        Spacer(Modifier.height(20.dp))
        Column(
            Modifier
                .padding(16.dp),
            Arrangement.Center
        ) {
            Text(
                text = "Cadastre-se",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight(500),
                fontSize = 35.sp,
            )
            Spacer(Modifier.height(25.dp))
            Text(
                text = "Nome: ",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight(250),
                fontSize = 25.sp,
            )
            Spacer(Modifier.height(15.dp))
            TextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Digite seu nome completo") },
                maxLines = 2
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = "Senha: ",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight(250),
                fontSize = 25.sp,
            )
            Spacer(Modifier.height(15.dp))
            TextField(
                value = senha,
                onValueChange = { senha = it },
                label = { Text("Senha") }
            )
            Spacer(Modifier.height(25.dp))
            Text(
                text = "Nome: ",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight(250),
                fontSize = 25.sp,
            )
            Spacer(Modifier.height(15.dp))
            TextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Digite seu nome completo") },
                maxLines = 2
            )
            Spacer(Modifier.height(25.dp))
            Text(
                text = "email: ",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight(250),
                fontSize = 25.sp,
            )
            Spacer(Modifier.height(15.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Digite seu e_mail completo") },
                maxLines = 2
            )
            Spacer(Modifier.height(25.dp))
            Text(
                text = "telefone: ",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight(250),
                fontSize = 25.sp,
            )
            Spacer(Modifier.height(15.dp))
            TextField(
                value = telefone,
                onValueChange = { telefone= it },
                label = { Text("Digite seu telefone") },
                maxLines = 2
            )
            Spacer(Modifier.height(25.dp))
            Text(
                text = "senha: ",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight(250),
                fontSize = 25.sp,
            )
            Spacer(Modifier.height(15.dp))
            TextField(
                value = senha,
                onValueChange = { senha = it },
                label = { Text("Digite sua senha") },
                maxLines = 2
            )
            Spacer(Modifier.height(25.dp))
            Text(
                text = "mensagem: ",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight(250),
                fontSize = 25.sp,
            )
            Spacer(Modifier.height(15.dp))
            TextField(
                value = mensagem,
                onValueChange = { mensagem = it },
                label = { Text("mensagem") },
                maxLines = 2
            )
        }
        Column(
            Modifier.padding(16.dp)
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6650a4)),
                onClick = {  val usuario = hashMapOf(
                    "nome" to nome,
                    "email" to email,
                    "telefone" to telefone,
                    "senha" to senha,
                    "mensagem" to mensagem
                )

                    db.collection("users")
                        .add(usuario)
                        .addOnSuccessListener {
                            nome = ""
                            senha = ""
                            db.collection("users")
                                .get()
                                .addOnSuccessListener { result ->
                                    usuarios = result.map { doc ->
                                        Usuario(
                                            nome = doc.getString("nome") ?: "",
                                            email = doc.getString("email") ?: "",
                                            telefone= doc.getString("telefone") ?: "",
                                            senha= doc.getString("senha") ?: "",
                                            mensagem = doc.getString("mensagem") ?: ""
                                        )
                                    }
                                }}},


            ) {
                Text(text = "Cadastrar")

            }
        }
    }
}

@Preview
@Composable
fun appPreview() {
    FirebaseTheme {
        app()
    }
}
