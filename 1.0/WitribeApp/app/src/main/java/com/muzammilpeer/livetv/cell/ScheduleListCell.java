package com.muzammilpeer.livetv.cell;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ranisaurus.baselayer.cell.BaseCell;
import com.ranisaurus.newtorklayer.models.Programme;
import com.ranisaurus.utilitylayer.base.BaseModel;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.view.CGSize;
import com.ranisaurus.utilitylayer.view.ImageUtil;
import com.muzammilpeer.livetv.R;
import com.muzammilpeer.livetv.view.SquareImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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

            startTime = startTime.substring(0, 2) + ":" + startTime.substring(2);
            endTime = endTime.substring(0, 2) + ":" + endTime.substring(2);


            tvProgrammeDurationLeft.setText("--/" + dataSource.getDuration() + " min");
            tvProgrammeDuration.setText(startTime + " - " + endTime);
            tvProgrammeName.setText(dataSource.getTitle());

            String imageUrl = dataSource.getProgrammeurl();

            ImageUtil.getImageFromUrl(CGSize.make((int) itemView.getResources().getDimension(R.dimen.cardview_thumbnail_height), (int) itemView.getResources().getDimension(R.dimen.cardview_thumbnail_height)),
                    ivProgrammeImage, pbProgrammeImage, imageUrl
            );

            //highlight only current time row
            Date currentDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            cal.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

            String startDateString = dataSource.getStart().substring(0, 4)
                    + "/" + dataSource.getStart().substring(4, 6)
                    + "/" + dataSource.getStart().substring(6, 8)
                    + " " + dataSource.getStart().substring(8, 10)
                    + ":" + dataSource.getStart().substring(10, 12);

            String endDateString = dataSource.getStop().substring(0, 4)
                    + "/" + dataSource.getStop().substring(4, 6)
                    + "/" + dataSource.getStop().substring(6, 8)
                    + " " + dataSource.getStop().substring(8, 10)
                    + ":" + dataSource.getStop().substring(10, 12);


            Date startedDate = new Date();
            Date endedDate = new Date();
            try {
                startedDate = dateFormat.parse(startDateString);
                endedDate = dateFormat.parse(endDateString);
            } catch (ParseException e) {
                Log4a.printException(e);
            }

            if (currentDate.getTime() >= startedDate.getTime() && currentDate.getTime() <= endedDate.getTime()) {
                itemView.setBackgroundColor(getBaseActivity().getResources().getColor(R.color.colorAccent));
            } else {
                itemView.setBackgroundColor(getBaseActivity().getResources().getColor(android.R.color.transparent));
            }

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