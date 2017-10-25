package com.example.oims001.ccfbleizing;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebviewFragment extends Fragment {

    private final String TAG = "WebviewFragment";

    private EditText edit_url;
    private WebView webView;

    public WebviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_webview, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        webView = (WebView) getActivity().findViewById(R.id.webview);
        edit_url = (EditText) getActivity().findViewById(R.id.edit_url);
        webView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);

        Button btn_enter = (Button) getActivity().findViewById(R.id.btn_enter);
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = edit_url.getText().toString();
                int startIndex = 0;
                int endIndex = 1;
                if (!url.equals("")) {
                    if (url.length() > 7) {
                        startIndex = 0;
                        endIndex = 7;
                    }
                    if (!url.substring(startIndex,endIndex).equals("http://")) {
                        if (url.length() > 4) {
                            startIndex = 0;
                            endIndex = 4;
                        }
                        if (!url.substring(startIndex, endIndex).equals("www.")) {
                            url = "www." + url;
                        }
                        url = "http://" + url;
                    }
                    Log.d(TAG, "URL = " + url);
                    webView.loadUrl(url);
                } else {
                    Toast.makeText(getActivity(), "Anda belum memasukkan sebuah alamat URL", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
