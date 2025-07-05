package com.example.cadapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cadapp.ui.theme.CadAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CadAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegistrationScreen()
                }
            }
        }
    }
}

@Composable
fun RegistrationScreen() {

    var nome by remember { mutableStateOf("") }
    var sobrenome by remember { mutableStateOf("") }
    var dataNascimento by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var nacionalidade by remember { mutableStateOf("") }
    var profissao by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Formulário de Cadastro",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )


        AppTextField(value = nome, onValueChange = { nome = it }, label = "Nome")
        AppTextField(value = sobrenome, onValueChange = { sobrenome = it }, label = "Sobrenome")
        AppTextField(value = dataNascimento, onValueChange = { dataNascimento = it }, label = "Data de Nascimento")
        AppTextField(value = genero, onValueChange = { genero = it }, label = "Gênero")
        AppTextField(value = telefone, onValueChange = { telefone = it }, label = "Telefone")
        AppTextField(value = celular, onValueChange = { celular = it }, label = "Celular")
        AppTextField(value = email, onValueChange = { email = it }, label = "Email")
        AppTextField(value = endereco, onValueChange = { endereco = it }, label = "Endereço")
        AppTextField(value = numero, onValueChange = { numero = it }, label = "Número")
        AppTextField(value = bairro, onValueChange = { bairro = it }, label = "Bairro")
        AppTextField(value = cidade, onValueChange = { cidade = it }, label = "Cidade")
        AppTextField(value = estado, onValueChange = { estado = it }, label = "Estado")
        AppTextField(value = cep, onValueChange = { cep = it }, label = "CEP")
        AppTextField(value = nacionalidade, onValueChange = { nacionalidade = it }, label = "Nacionalidade")
        AppTextField(value = profissao, onValueChange = { profissao = it }, label = "Profissão")

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { }) {
                Text("Enviar")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {
                nome = ""
                sobrenome = ""
                dataNascimento = ""
                genero = ""
                telefone = ""
                celular = ""
                email = ""
                endereco = ""
                numero = ""
                bairro = ""
                cidade = ""
                estado = ""
                cep = ""
                nacionalidade = ""
                profissao = ""
            }) {
                Text("Limpar")
            }
        }
    }
}

@Composable
fun AppTextField(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CadAppTheme {
        RegistrationScreen()
    }
}