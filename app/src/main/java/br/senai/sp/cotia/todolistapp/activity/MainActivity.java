package br.senai.sp.cotia.todolistapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import br.senai.sp.cotia.todolistapp.R;
import br.senai.sp.cotia.todolistapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //instancia o binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        // seta na content viw a raiz(root) do binding
        setContentView(binding.getRoot());
    }
}