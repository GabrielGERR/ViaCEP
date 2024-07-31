package com.example.puxardados

// MainActivity.kt
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.puxardados.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var viaCepService: ViaCepService
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val retrofit = RetrofitClient.retrofit
        viaCepService = retrofit.create(ViaCepService::class.java)


        binding.btnSearch.setOnClickListener {
            val cepID = binding.editTextcep.text
            val call = viaCepService.getAddress(cepID.toString())
            call.enqueue(object : Callback<AndressCEP> {
                override fun onResponse(call: Call<AndressCEP>, response: Response<AndressCEP>) {
                    val address = response.body()
                    if (response.isSuccessful) {
                        address?.let {
                            binding.textBairro.text = "Bairro:${it.bairro}"
                            binding.textComplemento.text = "Complemento:${it.complemento}"
                            binding.textddd.text = "DDD:${it.ddd}"
                            binding.textgia.text = "GIA:${it.gia}"
                            binding.textIBGE.text = "IBGE:${it.ibge}"
                            binding.textLocalidade.text = "Localidade:${it.localidade}"
                            binding.textLogradouro.text = "Logradouro:${it.logradouro}"
                            binding.textSiafi.text = "Siafi:${it.siafi}"
                            binding.textUF.text = "UF:${it.uf}"
                            binding.textUnidade.text = "Unidade:${it.unidade}"
                            Log.d(
                                "MainActivity",
                                "Endereço: ${it.logradouro}, ${it.bairro}, ${it.localidade}, ${it.uf}"
                            )
                        }
                    } else {
                        Log.e("MainActivity", "Resposta não bem-sucedida: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<AndressCEP>, t: Throwable) {
                    Log.e("MainActivity", "Erro: ${t.message}")
                }
            })
        }

    }
}
