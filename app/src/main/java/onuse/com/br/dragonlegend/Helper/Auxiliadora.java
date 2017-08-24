package onuse.com.br.dragonlegend.Helper;

import android.content.Context;
import android.content.res.TypedArray;

import java.util.Random;

import onuse.com.br.dragonlegend.R;

/**
 * Created by maico on 06/08/17.
 */

public class Auxiliadora {
    public static String NomeInimigo(int bixoid){
        String inimiogNome = null;
        if(bixoid == 0)
        {
            inimiogNome = "Serpente";
        }else if(bixoid == 1){
            inimiogNome = "Planta Diabólica";
        }else if(bixoid == 2){
            inimiogNome = "Imp";
        }else if(bixoid == 3){
            inimiogNome = "Aranha";
        }else if(bixoid == 4){
            inimiogNome = "Doutor Peste";
        }else if(bixoid == 5){
            inimiogNome = "Orc Furioso";
        }else if(bixoid == 6){
            inimiogNome = "Cerberus Infernal";
        }else if(bixoid == 7){
            inimiogNome = "Dragão Lendário";
        }
        return inimiogNome;
    }//Adiciona o nome do monstro conforme a id do mesmo

            //bixo = b.getString("Bixo");
    public static int Sortear0a100(){
        Random rand = new Random();
        int numeroSorteado = rand.nextInt(100); // Gives n such that 0 <= n < 20
        return numeroSorteado;
    }

    public static TypedArray FaseAtual(Context context, int faseId){
        TypedArray fase  = null;
        if(faseId == 0) {
         fase = context.getResources().obtainTypedArray(R.array.fase_um_images);
        }else if(faseId == 1) {
            fase = context.getResources().obtainTypedArray(R.array.fase_dois_images);
        }else if(faseId == 2) {
            fase = context.getResources().obtainTypedArray(R.array.fase_tres_images);
        }else if(faseId == 3) {
            fase = context.getResources().obtainTypedArray(R.array.fase_quatro_images);
        }else if(faseId == 4) {
            fase = context.getResources().obtainTypedArray(R.array.fase_cinco_images);
        }else if(faseId == 5) {
            fase = context.getResources().obtainTypedArray(R.array.fase_seis_images);
        }else if(faseId == 6) {
            fase = context.getResources().obtainTypedArray(R.array.fase_sete_images);
        }else if(faseId == 7) {
            fase = context.getResources().obtainTypedArray(R.array.fase_oito_images);
        }else if(faseId == 8) {
            fase = context.getResources().obtainTypedArray(R.array.fase_nove_images);
        }
        return fase;
    }//Retorna como um typedarray os recursos de fase conforme a ID da fase

    public static String[] FaseFalasAtual(Context context, int faseId){
        String[] falas  = null;
        if(faseId == 0) {
            falas = context.getResources().getStringArray(R.array.fase_um);
        }else if(faseId == 1) {
            falas = context.getResources().getStringArray(R.array.fase_dois);
        }else if(faseId == 2) {
            falas = context.getResources().getStringArray(R.array.fase_tres);
        }else if(faseId == 3) {
            falas = context.getResources().getStringArray(R.array.fase_quatro);
        }else if(faseId == 4) {
            falas = context.getResources().getStringArray(R.array.fase_cinco);
        }else if(faseId == 5) {
            falas = context.getResources().getStringArray(R.array.fase_seis);
        }else if(faseId == 6) {
            falas = context.getResources().getStringArray(R.array.fase_sete);
        }else if(faseId == 7) {
            falas = context.getResources().getStringArray(R.array.fase_oito);
        }else if(faseId == 8) {
            falas = context.getResources().getStringArray(R.array.fase_nove);
        }
        return falas;
    }//Retorna como um typedarray os recursos de fase conforme a ID da fase
}
