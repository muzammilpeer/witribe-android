package com.witribe.witribeapp.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ranisaurus.baselayer.adapter.GeneralBaseAdapter;
import com.ranisaurus.baselayer.fragment.BaseFragment;
import com.ranisaurus.utilitylayer.file.FileUtil;
import com.ranisaurus.utilitylayer.file.model.FileInfoModel;
import com.ranisaurus.utilitylayer.view.WindowUtil;
import com.witribe.witribeapp.R;
import com.witribe.witribeapp.cell.RecordedVideoListCell;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 11/9/15.
 */
public class RecordVideoListFragment extends BaseFragment {
    // UI references.
    @Bind(R.id.rc_recordvideolist)
    RecyclerView rcRecordVideoList;

    @Bind(R.id.ll_recordvideolist)
    LinearLayout llRecordVideoList;

    @Bind(R.id.pb_recordvideolist)
    ProgressBar pbRecordVideoList;

    GeneralBaseAdapter<RecordedVideoListCell> dataGeneralBaseAdapter;

    public static RecordVideoListFragment newInstance() {
        RecordVideoListFragment fragment = new RecordVideoListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, R.layout.fragment_recordvideolist);

        if (getArguments() != null) {
        }

        return mView;
    }


    @Override
    public void initViews() {
        super.initViews();

        getBaseActivity().isFullScreenOptionEnable = false;
        getBaseActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getBaseActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        WindowUtil.showSystemUi(getBaseActivity());
        getBaseActivity().showToolBar();
        getBaseActivity().getTabLayoutView().setVisibility(View.GONE);


        //app:layout_scrollFlags="scroll|enterAlways"
        AppBarLayout.LayoutParams params =
                (AppBarLayout.LayoutParams) getBaseActivity().getMainToolbar().getLayoutParams();
//        params.setScrollFlags(0);
        getBaseActivity().getMainToolbar().setLayoutParams(params);
        getBaseActivity().getMainToolbar().setVisibility(View.VISIBLE);
        getBaseActivity().getMainToolbar().setCollapsible(false);

        getBaseActivity().getSupportActionBar().setTitle(getString(R.string.menu_recording_list));
    }

    @Override
    public void initObjects() {
        super.initObjects();

    }

    @Override
    public void initListenerOrAdapter() {
        super.initListenerOrAdapter();


        dataGeneralBaseAdapter = new GeneralBaseAdapter<RecordedVideoListCell>(mContext, R.layout.row_recorded_video_list, RecordedVideoListCell.class, this.getLocalDataSource());

        rcRecordVideoList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcRecordVideoList.setLayoutManager(layoutManager);
        rcRecordVideoList.setAdapter(dataGeneralBaseAdapter);
        dataGeneralBaseAdapter.notifyDataSetChanged();


        if (this.getLocalDataSource().size() < 1)
        {
            String folderPath = FileUtil.createFolder(getString(R.string.app_name));
            showLoader();
            getLocalDataSource().clear();


            ListFilesAsync listFilesAsync = new ListFilesAsync();
            listFilesAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, folderPath);
        }else {
            hideLoader(false);
            dataGeneralBaseAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void initNetworkCalls() {
        super.initNetworkCalls();


    }


    @Override
    protected void showLoader() {
        pbRecordVideoList.setVisibility(View.VISIBLE);
        rcRecordVideoList.setVisibility(View.GONE);
        llRecordVideoList.setVisibility(View.GONE);
    }

    @Override
    protected void hideLoader(boolean isError) {
        pbRecordVideoList.setVisibility(View.GONE);

        if (isError) {
            llRecordVideoList.setVisibility(View.VISIBLE);
            rcRecordVideoList.setVisibility(View.GONE);
        } else {
            llRecordVideoList.setVisibility(View.GONE);
            rcRecordVideoList.setVisibility(View.VISIBLE);
        }
    }


    class ListFilesAsync extends AsyncTask<String, Void, ArrayList<FileInfoModel>> {
        public ListFilesAsync() {
        }


        @Override
        protected ArrayList<FileInfoModel> doInBackground(String... params) {
            return FileUtil.listFiles(params[0]);
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<FileInfoModel> fileInfoModels) {
            super.onPostExecute(fileInfoModels);

            getLocalDataSource().addAll(fileInfoModels);
            dataGeneralBaseAdapter.notifyDataSetChanged();
            if (getLocalDataSource().size() < 1) {
                hideLoader(true);
            } else {
                hideLoader(false);
            }

        }
    }
}
