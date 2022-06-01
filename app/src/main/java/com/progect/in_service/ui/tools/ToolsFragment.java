package com.progect.in_service.ui.tools;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.progect.in_service.R;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
private TextView tx;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);

        tx = root.findViewById(R.id.text_tools);
        tx.setText(Html.fromHtml("<b>Наши контакты: </b><br>"
                +"e-mail: <br>"
                +"KZ: <br>"
        +"<b>1. После оформления заказа мной заказ никто не взял.</b><br>"
        +"Повторите заказ.<br>"
        +"Попробуйте увеличить стоимость заказа.<br>"
        +"<b>2. Как оставить отзыв о мастере.</b><br>"
        +"авыдлповапдлвоапдлваповдлповадлповадпловапдлвоадло<br>"
        +"<b>3. Вам не понравился мастер? Или возникла конфликтная ситуация?</b><br>"
        +"Оставьте негативный отзыв о мастере. После работы: напишите нам через форму обратной связи в боковом меню приложения или на ПОЧТУ. Послу проверки достоверности данных нарушители будут заблокированы.<br>"
        +"<b>4. Забытые вещи</b><br>"
        +"Напишите на ПОЧТУ. Вы можете также позвонить в службу поддержки клиентов по тел. ТЕЛЕФОН (Казахстан). Будьте готовы назвать время и данные мастера, по возможности. Мы поможем решить эту ситуацию."));

        return root;
    }
}