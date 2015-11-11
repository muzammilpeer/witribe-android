package com.witribe.witribeapp.cell;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ranisaurus.baselayer.cell.BaseCell;
import com.ranisaurus.newtorklayer.models.Programme;
import com.ranisaurus.utilitylayer.base.BaseModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.witribe.witribeapp.R;
import com.witribe.witribeapp.view.SquareImageView;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 11/12/15.
 */
public class ScheduleListCell extends BaseCell implements View.OnClickListener {

    @Bind(R.id.iv_programme_image)
    SquareImageView ivProgrammeImage;


    @Bind(R.id.tv_programme_name)
    TextView tvProgrammeName;

    @Bind(R.id.tv_programme_duration)
    TextView tvProgrammeDuration;


    @Bind(R.id.tv_programme_duration_left)
    TextView tvProgrammeDurationLeft;

    @Bind(R.id.pb_programme_image)
    ProgressBar pbProgrammeImage;

    public ScheduleListCell(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);

    }

    @Override
    public void updateCell(BaseModel model) {
        position = this.getAdapterPosition();

        mDataSource = model;

        if (model instanceof Programme) {
            Programme dataSource = (Programme) model;

            String startTime = dataSource.getStart().substring(8);
            String endTime = dataSource.getStop().substring(8);

            startTime = startTime.substring(0,2) + ":"+startTime.substring(2);
            endTime = endTime.substring(0,2) + ":"+endTime.substring(2);

            tvProgrammeDurationLeft.setText("--/" + dataSource.getDuration() + " min");
            tvProgrammeDuration.setText(startTime + " - " + endTime);
            tvProgrammeName.setText(dataSource.getTitle());

            String imageUrl = dataSource.getProgrammeurl().replaceAll(" ", "%20");
            Picasso.with(itemView.getContext())
                    .load(imageUrl)
                    .resize(100, 100)
                    .centerInside()
                    .into(ivProgrammeImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            pbProgrammeImage.setVisibility(View.GONE);
                            ivProgrammeImage.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {
                            pbProgrammeImage.setVisibility(View.GONE);
                            ivProgrammeImage.setVisibility(View.VISIBLE);
                        }
                    });


        } else {
            ivProgrammeImage.setImageBitmap(null);
        }

    }

    @Override
    public void onClick(View v) {
        if (v == itemView) {
            Programme selectedModel = (Programme) mDataSource;
//            getBaseActivity().replaceFragment(RecordedVideoDetailPlayScreenFragment.newInstance(selectedModel), R.id.container_main);
        }
    }

}