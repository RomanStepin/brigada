package com.example.pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AvatarActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.grid)
    GridView grid;
    @BindView(R.id.textInputEditText_login)
    EditText textInputEditText_login;
    @BindView(R.id.button_send_login)
    Button button_send_login;

    @BindView(R.id.textInputLayout_login)
    TextInputLayout textInputLayout_login;


    ArrayList<Integer> avatarList;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        ButterKnife.bind(this);

        sharedPreferences = ((App) getApplication()).getPrefs();
        String name = sharedPreferences.getString("name", "");
        int avatar_id = sharedPreferences.getInt("avatar", R.drawable.a1);
        textInputEditText_login.setText(name);


        button_send_login.setOnClickListener(this);
        avatarList  =  new ArrayList<>(Arrays.asList(R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6,R.drawable.a7,R.drawable.a8));
        SetNameListener setNameListener = (name_id -> {textInputEditText_login.setText(getString(name_id));});
        SaveAvatarListener saveAvatarListener = (id -> {
            sharedPreferences = ((App)getApplication()).getPrefs();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("avatar", id);
            editor.apply();
        });
        grid.setAdapter(new GridViewAdapter(avatarList, setNameListener, saveAvatarListener, avatar_id));
    }

    @Override
    public void onClick(View v) {
        if (!empty_editText_check()) {
            sharedPreferences = ((App) getApplication()).getPrefs();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", textInputEditText_login.getText().toString());
            editor.apply();
            Log.d("LOGGG", "ТЕПЕРЬ ТОЧНО ОТКРЫВАЕМ ЧАТ");
            ChatActivity.startInNewTask(this);
        }
    }

    boolean empty_editText_check() {
        if (textInputEditText_login.getText().toString().equals("")) {
            textInputLayout_login.setError(getString(R.string.empty_editText));
            return true;
        } else return false;
    }

    public static void startInNewTask(Context context)
    {
        Intent starterIntent = new Intent(context, AvatarActivity.class);
        starterIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starterIntent);
    }

}

interface SetNameListener
{
    void setName_id(Integer name_id);
}

interface SaveAvatarListener
{
    void saveAvatar_id(Integer avatar_id);
}

class GridViewAdapter extends BaseAdapter
{

    ArrayList<Integer> idList;
    int act;
    SetNameListener setNameListener;
    SaveAvatarListener saveAvatarListener;
    ArrayList<Integer> nameList  =  new ArrayList<>(Arrays.asList(R.string.name1,R.string.name2,R.string.name3,R.string.name4,R.string.name5,R.string.name6,R.string.name7,R.string.name8));

    public GridViewAdapter(ArrayList<Integer> arrayList, SetNameListener setNameListener, SaveAvatarListener saveAvatarListener, int avatar_id)
    {
        this.idList = arrayList;
        this.setNameListener = setNameListener;
        this.saveAvatarListener = saveAvatarListener;

        act = idList.indexOf(avatar_id);
    }


    @Override
    public int getCount() {
        return idList.size();
    }

    @Override
    public Object getItem(int position) {
        return idList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(parent.getContext());
        if (position != act)
        imageView.setAlpha((float) 0.6);
        imageView.setImageResource(idList.get(position));
        imageView.setOnClickListener(v -> {
            act = position;
            notifyDataSetChanged();
            v.setAlpha(1);
            setNameListener.setName_id(nameList.get(position));
            saveAvatarListener.saveAvatar_id(idList.get(position));
        });
        return imageView;
    }


}