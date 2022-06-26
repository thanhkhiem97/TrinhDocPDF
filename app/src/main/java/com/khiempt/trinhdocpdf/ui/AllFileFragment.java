package com.khiempt.trinhdocpdf.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.khiempt.trinhdocpdf.BuildConfig;
import com.khiempt.trinhdocpdf.R;
import com.khiempt.trinhdocpdf.constain.Constants;
import com.khiempt.trinhdocpdf.ui.data.PDFAdapter;
import com.khiempt.trinhdocpdf.ui.data.PDFMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AllFileFragment extends Fragment {
    public static final String EMAIL_KEY = "email_key";
    public static final String SHARED_PREFS = "shared_prefs";
    // key for storing password.
    int mType;
    int mPosition;
    int mBotton;

    public AllFileFragment(int type, int position, int mBotton) {
        mType = type;
        mPosition = position;
        this.mBotton = position;
    }

    public static SharedPref sharedpreferences;
    PDFAdapter adapter;
    ArrayList<String> pdfList;
    List<PDFMode> mList;
    List<PDFMode> mListAdapter = new ArrayList<>();
    List<PDFMode> mListShae = new ArrayList<>();
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pdf, container, false);
        String path1 = Environment.getExternalStorageDirectory().getPath();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        sharedpreferences = new SharedPref(getActivity());
        mList = new ArrayList<>();
        Log.d("KHKHKH",mType+"");
        getPdfList("pptx");
        getPdfList("pdf");
        getPdfList("xlsx");
        getPdfList("docx");

        swipeRefresh = rootView.findViewById(R.id.layout_swipe_refresh);
        swipeRefresh.setOnRefreshListener(() -> {
            swipeRefresh.setRefreshing(true);
            refreshLayout();
        });
        mListShae = getList();
        mListAdapter.clear();
        mListAdapter.addAll(mList);

        for (int i = 0; i < mListAdapter.size(); i++)
            for (int j = 0; j < mListShae.size(); j++) {
                if (mListShae.get(j).getmDuongDang().equals(mListAdapter.get(i).getmDuongDang()))
                    mListAdapter.get(i).setDauTrang(true);
            }
        int n = mListAdapter.size();
        if (sharedpreferences.getString(Constants.KEY_BOTTOM).equals("3")) {
            mListAdapter.clear();
            mListAdapter.addAll(mListShae);

            if (mPosition == 2)
                Log.d("KKKKKKKKK", new Gson().toJson(mListAdapter));
        }
        Log.d("TTTTTTTTT", mPosition + " " + new Gson().toJson(mList));
        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PDFAdapter(getContext(), mListAdapter, mType, mPosition) {
            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.img_dautrang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mListAdapter.get(position).isDauTrang()) {
                            mListAdapter.get(position).setDauTrang(false);
                            for (int i = 0; i < mListShae.size(); i++) {
                                if (mListShae.get(i).getmDuongDang().equals(mListAdapter.get(position).getmDuongDang())) {
                                    mListShae.remove(i);
                                    break;
                                }
                            }

                        } else {
                            mListAdapter.get(position).setDauTrang(true);
                            mListShae.add(mListAdapter.get(position));
                        }
                        setList(mListShae);
                        if (sharedpreferences.getString(Constants.KEY_BOTTOM).equals("3")) {
                            mListAdapter.remove(position);
                        }
                        adapter.notifyDataSetChanged();

                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent intent = new Intent(getContext(), Chitiet.class);
//                        intent.putExtra("NAME", mListAdapter.get(position).getmDuongDang());
//                        startActivity(intent);

                        String name = mListAdapter.get(position).getmDuongDang();
                        String fileUrl = name;
                        File file = new File(fileUrl);
                        String mime = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

                        Uri theUri = FileProvider.getUriForFile(Objects.requireNonNull(getActivity()),
                                BuildConfig.APPLICATION_ID + ".provider", file);

                        Intent intent = new Intent();

                        intent.setAction(android.content.Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                        intent.setDataAndType(theUri, mime);

                        startActivity(intent);

                    }
                });
                holder.img_thongtin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popup = new PopupMenu(getContext(), holder.img_thongtin);
                        popup.inflate(R.menu.info);
                        if (mListAdapter.get(position).isDauTrang()) {
                            popup.getMenu().getItem(0).setTitle(getString(R.string.text_remove_from_bookmark));
                        } else
                            popup.getMenu().getItem(0).setTitle(getString(R.string.text_add_to_bookmark));
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.action_dautrang:
                                        if (mListAdapter.get(position).isDauTrang()) {
                                            mListAdapter.get(position).setDauTrang(false);
                                            for (int i = 0; i < mListShae.size(); i++) {
                                                if (mListShae.get(i).getmDuongDang().equals(mListAdapter.get(position).getmDuongDang())) {
                                                    mListShae.remove(i);
                                                    Toast.makeText(getContext(), getString(R.string.text_removed_bookmars), Toast.LENGTH_SHORT).show();
                                                    break;
                                                }
                                            }

                                        } else {
                                            mListAdapter.get(position).setDauTrang(true);
                                            mListShae.add(mListAdapter.get(position));
                                            Toast.makeText(getContext(), getString(R.string.text_added_bookmars), Toast.LENGTH_SHORT).show();
                                        }
                                        setList(mListShae);
                                        adapter.notifyDataSetChanged();
                                        break;
                                    case R.id.action_rename:
                                        final View view = getLayoutInflater().inflate(R.layout.dialog_rename, null);
                                        AlertDialog alertDialog = new AlertDialog.Builder(requireContext()).create();
                                        alertDialog.setTitle(getString(R.string.text_rename));
                                        alertDialog.setCancelable(false);

                                        final TextInputEditText etComments = (TextInputEditText) view.findViewById(R.id.tv_hovaten);
                                        etComments.setText(mListAdapter.get(position).getmTenFile());

                                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.text_rename), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                renameFile(mListAdapter.get(position).getmDuongDang().replace(mListAdapter.get(position).getmTenFile() + "." + mListAdapter.get(position).getDuoiFile(),
                                                        ""), mListAdapter.get(position).getmTenFile() + "." + mListAdapter.get(position).getDuoiFile(),
                                                        etComments.getText().toString() + "." + mListAdapter.get(position).getDuoiFile());
                                            }
                                        });


                                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.text_close), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                alertDialog.dismiss();
                                            }
                                        });


                                        alertDialog.setView(view);
                                        alertDialog.show();
                                        break;
                                    case R.id.action_chiase:
                                        File file = new File(mListAdapter.get(position).getmDuongDang());
                                        Intent intent = new Intent(Intent.ACTION_SEND);
                                        intent.setType("application/pdf");
                                        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file));
                                        Log.d("THANH", Uri.parse("file://" + file).toString());
                                        intent.putExtra(Intent.EXTRA_SUBJECT, "Sử dụng một trong những Trình đọc PDF tốt nhất Tải xuống miễn phí ngay bây giờ! ");
                                        startActivity(Intent.createChooser(intent, "share"));
                                        break;
                                    case R.id.action_xoatailieu:
                                        File file1 = new File(mListAdapter.get(position).getmDuongDang());
                                        Log.d("THANH", String.valueOf(Environment.getExternalStorageDirectory()));
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                                        builder1.setMessage(getString(R.string.text_delete_file_confirmation));
                                        builder1.setTitle(getString(R.string.text_delete_file));
                                        builder1.setCancelable(false);
                                        builder1.setPositiveButton(
                                                getString(R.string.text_delete),
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        try {

                                                            file1.delete();
                                                            Toast.makeText(getContext(), getString(R.string.text_delete_success), Toast.LENGTH_SHORT).show();
                                                            for (int i = 0; i < mListShae.size(); i++) {
                                                                if (mListShae.get(i).getmDuongDang().equals(mListAdapter.get(position).getmDuongDang())) {
                                                                    mListShae.remove(i);
                                                                    break;
                                                                }
                                                            }
                                                            mListAdapter.remove(position);
                                                            adapter.notifyDataSetChanged();

                                                        } catch (Exception e) {
                                                            Toast.makeText(getContext(), getString(R.string.text_delete_error), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                        builder1.setNegativeButton(getString(R.string.text_cancel), new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                        AlertDialog alert11 = builder1.create();
                                        alert11.show();
                                        break;
                                    case R.id.action_duongtat:

                                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                                        startMain.addCategory(Intent.CATEGORY_HOME);
                                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(startMain);
                                        break;
                                    case R.id.action_info:
                                        final View view1 = getLayoutInflater().inflate(R.layout.dialog_thongtin, null);
                                        AlertDialog alertDialog1 = new AlertDialog.Builder(requireContext()).create();
                                        alertDialog1.setCancelable(false);

                                        TextView tvTitle = view1.findViewById(R.id.tv_title);
                                        TextView tvLoaiTep = view1.findViewById(R.id.tv_loaitep);
                                        TextView tvDuongDan = view1.findViewById(R.id.tv_path);
                                        TextView tvKichThuoc = view1.findViewById(R.id.tv_kichthuoc);
                                        TextView tvSuadoi = view1.findViewById(R.id.tv_suadoi);
                                        tvTitle.setText(mListAdapter.get(position).getmTenFile());
                                        tvLoaiTep.setText(mListAdapter.get(position).getDuoiFile().toUpperCase());
                                        tvDuongDan.setText(mListAdapter.get(position).getmDuongDang());
                                        String suffix = "";
                                        int size = mList.get(position).getSize();
                                        if (size >= 1024) {
                                            suffix = "MB";
                                            size /= 1024;

                                        } else {
                                            suffix = " KB";
                                        }

                                        tvKichThuoc.setText(size + " " + suffix);
                                        tvSuadoi.setText(mListAdapter.get(position).getTime());
                                        alertDialog1.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.text_ok).toLowerCase(), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                alertDialog1.dismiss();
                                            }
                                        });


                                        alertDialog1.setView(view1);
                                        alertDialog1.show();
                                        break;
                                    default:

                                        break;
                                }
                                return true;
                            }
                        });

                        popup.show();
                    }
                });

            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setClipToPadding(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        for (int i = 0; i < mListAdapter.size(); i++)
            for (int j = 0; j < mListShae.size(); j++) {
                if (mListShae.get(j).getmDuongDang().equals(mListAdapter.get(i).getmDuongDang())) {
                    mListAdapter.get(i).setDauTrang(true);
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        return rootView;
    }

    public void refreshLayout() {
        swipeRefresh.setRefreshing(false);
        getPdfList("pptx");
        getPdfList("pdf");
        getPdfList("xlsx");
        getPdfList("docx");


        mListShae = getList();
        mListAdapter = new ArrayList<>();
        mListAdapter.addAll(mList);
        Log.d("AAAAAAA", new Gson().toJson(mList));

        for (int i = 0; i < mListAdapter.size(); i++)
            for (int j = 0; j < mListShae.size(); j++) {
                if (mListShae.get(j).getmDuongDang().equals(mListAdapter.get(i).getmDuongDang()))
                    mListAdapter.get(i).setDauTrang(true);
            }
        if (sharedpreferences.getString(Constants.KEY_BOTTOM).equals("3")) {
            mListAdapter.clear();
            mListAdapter.addAll(mListShae);

            if (mPosition == 2)
                Log.d("KKKKKKKKK", new Gson().toJson(mListAdapter));
        }
        if (mListShae.size() == 0) {
            for (int i = 0; i < mListAdapter.size(); i++) {
                mListAdapter.get(i).setDauTrang(false);
            }
            adapter.notifyDataSetChanged();

        }
        adapter.notifyDataSetChanged();
    }

    private boolean rename(File from, File to) {
        return from.getParentFile().exists() && from.exists() && from.renameTo(to);
    }

    public static void renameFile(String file, String oldName, String newName) {
        File dir = new File(file);
        if (dir.exists()) {
            File from = new File(dir, oldName);
            File to = new File(dir, newName);
            from.renameTo(to);
        }
    }

    private void button1Clicked(ImageView imageView) {
        // When user click on the Button 1, create a PopupMenu.
        // And anchor Popup to the Button 2.
        PopupMenu popup = new PopupMenu(getContext(), imageView);
        popup.inflate(R.menu.info);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_dautrang:
                        Toast.makeText(getContext(), "Bookmark", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_rename:
                        Toast.makeText(getContext(), "action_rename", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_chiase:
                        Toast.makeText(getContext(), "action_chiase", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_xoatailieu:
                        Toast.makeText(getContext(), "action_xoatailieu", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_duongtat:
                        Toast.makeText(getContext(), "action_duongtat", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_info:
                        Toast.makeText(getContext(), "Saction_info", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        popup.show();
    }

    private boolean menuItemClicked(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_dautrang:
                Toast.makeText(getContext(), "Bookmark", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_rename:
                Toast.makeText(getContext(), "action_rename", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_chiase:
                Toast.makeText(getContext(), "action_chiase", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_xoatailieu:
                Toast.makeText(getContext(), "action_xoatailieu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_duongtat:
                Toast.makeText(getContext(), "action_duongtat", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_info:
                Toast.makeText(getContext(), "Saction_info", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    public ArrayList<String> getPdfList(String string) {
        pdfList = new ArrayList<>();
        Uri collection;
        final String[] projection = new String[]{
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.MIME_TYPE,
        };

        final String sortOrder = MediaStore.Files.FileColumns.DATE_ADDED + " DESC";

        final String selection = MediaStore.Files.FileColumns.MIME_TYPE + " = ?";

        final String pptx = MimeTypeMap.getSingleton().getMimeTypeFromExtension(string);
        final String[] selectionArgs = new String[]{pptx};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            collection = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            collection = MediaStore.Files.getContentUri("external");
        }


        try (Cursor cursor = getActivity().getContentResolver().query(collection, projection, selection, selectionArgs, sortOrder)) {
            assert cursor != null;

            if (cursor.moveToFirst()) {
                int columnData = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
                int columnName = cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME);
                String columnSise = String.valueOf(cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE));
                int dateTakenColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);
                int dateModifiedColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED);
                //ArrayList pdfList = new ArrayList<>();
                int i = 0;
                do {
                    pdfList.add((cursor.getString(columnData)));
                    System.out.println(cursor.getString(columnData));
                    File file = new File((cursor.getString(columnData)));
                    int file_size = Integer.parseInt(String.valueOf(file.length() / 1024));

                    int logo = 0;
                    if (string.equals("pptx"))
                        logo = R.drawable.ic_ppt;
                    else if (string.equals("pdf"))
                        logo = R.drawable.ic_pdf;
                    else if (string.equals("xlsx"))
                        logo = R.drawable.iv_excel;
                    if (string.equals("docx"))
                        logo = R.drawable.ic_word;

                    mList.add(new PDFMode(i, file_size, convertLongToDate(dateTakenColumn), cursor.getString(columnName).replace("." + string, ""),
                            cursor.getString(columnData), logo, mType, string, false));
                    i++;
                    //you can get your pdf files
                } while (cursor.moveToNext());
            }
        }
        return pdfList;
    }

    private int getType(String s) {
        switch (s) {
            case "pptx":
                mType = 5;
                break;
            case "pdf":
                mType = 2;
                break;
            case "xlsx":
                mType = 4;
                break;
            case "docx":
                mType = 3;
                break;
        }
        return mType;
    }

    public static void setList(List<PDFMode> modeList) {
        Gson gson = new Gson();
        JsonArray jsonArray = gson.toJsonTree(modeList).getAsJsonArray();
        String strJson = jsonArray.toString();
        sharedpreferences.putString(Constants.KEY_LIST, strJson);
    }

    public static void setAllList(List<PDFMode> modeList) {
        Gson gson = new Gson();
        JsonArray jsonArray = gson.toJsonTree(modeList).getAsJsonArray();
        String strJson = jsonArray.toString();
        sharedpreferences.putString(Constants.KEY_LIST, strJson);
    }

    public static List<PDFMode> getList() {
        List<PDFMode> list = new ArrayList<>();
        String strJsonArry = sharedpreferences.getString(Constants.KEY_LIST);
        try {
            JSONArray jsonArray = new JSONArray(strJsonArry);
            JSONObject jsonObject;
            PDFMode pdfMode;
            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                pdfMode = gson.fromJson(jsonObject.toString(), PDFMode.class);
                list.add(pdfMode);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    String convertLongToDate(long time) {
        long val = time;
        Date date = new Date(val);
        SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
        String dateText = df2.format(date);
        return dateText;
    }

}
