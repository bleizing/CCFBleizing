package com.example.oims001.ccfbleizing;


import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.Context.DOWNLOAD_SERVICE;


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
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);

        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.clearCache(true);
        webView.clearHistory();
        webView.setDownloadListener(downloadListener);
//        webView.setDownloadListener(new DownloadListener() {
//            @Override
//            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
//
//                DownloadManager.Request request = new DownloadManager.Request(
//                        Uri.parse(url));
//
//                request.setMimeType(mimeType);
//
//                String cookies = CookieManager.getInstance().getCookie(url);
//
//                request.addRequestHeader("cookie", cookies);
//
//                request.addRequestHeader("User-Agent", userAgent);
//
//                request.setDescription("Downloading file...");
//
//                request.setTitle(URLUtil.guessFileName(url, contentDisposition,
//                        mimeType));
//
//                request.allowScanningByMediaScanner();
//
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                request.setDestinationInExternalFilesDir(getActivity(),
//                        Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(
//                                url, contentDisposition, mimeType));
//                DownloadManager dm = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
//                dm.enqueue(request);
//                Toast.makeText(getActivity(), "Downloading File",
//                        Toast.LENGTH_LONG).show();
//            }});

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

    private DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {

            DownloadManager.Request request = new DownloadManager.Request(
                    Uri.parse(url));

            request.setMimeType(mimeType);

            String cookies = CookieManager.getInstance().getCookie(url);

            request.addRequestHeader("cookie", cookies);

            request.addRequestHeader("User-Agent", userAgent);

            request.setDescription("Downloading file...");

            request.setTitle(URLUtil.guessFileName(url, contentDisposition,
                    mimeType));

            request.allowScanningByMediaScanner();

            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalFilesDir(getActivity(),
                    Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(
                            url, contentDisposition, mimeType));
            DownloadManager dm = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
            dm.enqueue(request);
            Toast.makeText(getActivity(), "Downloading File",
                    Toast.LENGTH_LONG).show();
        }
    };
}
