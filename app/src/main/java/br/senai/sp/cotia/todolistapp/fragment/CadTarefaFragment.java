package br.senai.sp.cotia.todolistapp.fragment;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

import br.senai.sp.cotia.todolistapp.R;
import br.senai.sp.cotia.todolistapp.database.AppDataBase;
import br.senai.sp.cotia.todolistapp.databinding.FragmentCadTarefaBinding;
import br.senai.sp.cotia.todolistapp.model.Tarefa;


public class CadTarefaFragment extends Fragment {

    //variaveis para ano mes e dia

    int year, month,day;

    //variavel para data atual

    Calendar dataAtual;

    //variavel para data formatada
    String dataFormatada ="";
    //variavel para a DataBase
    AppDataBase dataBase;
    private FragmentCadTarefaBinding binding;
    //variavel datepicker
    private DatePickerDialog datePicker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //instaciando a dataBase
        dataBase = AppDataBase.getDataBase(getContext());


        binding = FragmentCadTarefaBinding.inflate(getLayoutInflater(), container, false);

        //instaciar a data atual
        dataAtual = Calendar.getInstance();
        //obter ano mes e dia da data atual
        year = dataAtual.get(Calendar.YEAR);
        month = dataAtual.get(Calendar.MONTH);
        day = dataAtual.get(Calendar.DAY_OF_MONTH);

        //instaciando o datePicker
        datePicker = new DatePickerDialog(getContext(), (datePicker,ano,mes, dia)->{
            //ao escolher uma data no date picker ira cair aqui!!
            //passar para as varieaveis globais
            year = ano;
            month = mes;
            day = dia;

            //formatar a data ---
            dataFormatada = String.format("%02d/%02d/%04d", day, month+1,year );
            //aplicadno a data formatada no botao
            binding.btSeleData.setText(dataFormatada);

        },year,month,day);


        //ação do click do bt selação data
        binding.btSeleData.setOnClickListener(v ->{
                datePicker.show();
                });
        //listear do botao salvar ---
        binding.btsalvar.setOnClickListener(v ->{
           if(binding.titulo.getText().toString().isEmpty()){
                Toast.makeText( getContext(), R.string.escTitulo, Toast.LENGTH_SHORT).show();
           }else if(dataFormatada.isEmpty()){
               Toast.makeText(getContext(), R.string.dataInse, Toast.LENGTH_SHORT).show();
           }else{
               Toast.makeText(getContext(), R.string.ParaOk, Toast.LENGTH_SHORT).show();
               //criar tarefa
               Tarefa tarefa = new Tarefa();
               //populando a tarefa
               tarefa.setTitulo(binding.titulo.getText().toString());
               tarefa.setDescricao(binding.descTarefa.getText().toString());
               tarefa.setDataCriacao(dataAtual.getTimeInMillis());
               // criar um calendar
               Calendar dataPrevista = Calendar.getInstance();
               //muda a data para a data escolhida no datepicker
                dataPrevista.set(year,month,day);
                //passa os mileSegundos da data para a data prevista
               tarefa.setDataPrevista(dataPrevista.getTimeInMillis());
               //salvar a tarefa
               new InsertTarefa().execute(tarefa);

           }
        });
        //retornando
        return binding.getRoot();
    }
    //criar AsyncTask para inserir tarefa
    private class InsertTarefa extends AsyncTask<Tarefa, Void, String > {

        //pegando as coisas antes de executar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Tarefa... tarefas){
            //pegar a tarefa a partir do vetor
            Tarefa t = tarefas[0];
            try {
                //chamar o metodo da dataBase para savlar

                dataBase.getTarefaDao().insert(t);
                //retornar
                return "ok";
            }catch (Exception e){
                e.printStackTrace();
                //retornar a ,mensage de erro
                return e.getMessage();
            }
        }

        //obter informações do que esta acontecendo
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        //após execução
        @Override
        protected void onPostExecute(String result) {
        if(result.equals("ok")){
            Log.w("RESULT","IUPIIIIIIIII É GOL DO TIMÃO CARALHOOOOOOOOOOOOOOOOOOOOO");
            Toast.makeText(getContext(), "Tarefa Inserida Com Sucesso ", Toast.LENGTH_SHORT).show();
            //voltar ao fragment, invocando o botão de voltar 
            getActivity().onBackPressed();
        }else{
            Log.w("RESULT", result);
            Toast.makeText(getActivity(), result , Toast.LENGTH_SHORT).show();
        }
        }
    }
}