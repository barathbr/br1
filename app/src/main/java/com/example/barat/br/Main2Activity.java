package com.example.barat.br;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    ProgressBar mprogressBar;
    private static String TAG= "MainActivity";
    private float[] yData = {60.0f,38.0f,12.0f};
    private String[] xData = {"Kannada","Malayalam","English"};
    PieChart pieChart;
    HorizontalBarChart mChart;
    int pStatus = 0;
    private Handler handler = new Handler();
    TextView tv;
    RecyclerView horizontal_recycler_view;
    HorizontalAdapter horizontalAdapter;
    private List<Data> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //recycler code
        horizontal_recycler_view= (RecyclerView) findViewById(R.id.horizontal_recycler_view);

        data = fill_with_data();


        horizontalAdapter=new HorizontalAdapter(data, getApplication());

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_view.setLayoutManager(horizontalLayoutManager);
        horizontal_recycler_view.setAdapter(horizontalAdapter);
        //end

        Spinner spinner= findViewById(R.id.spinner);
        List<String> categories = new ArrayList<String>();
        categories.add("ALL");
        categories.add("Admin");
        categories.add("User1");
        categories.add("User2");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        mprogressBar =(ProgressBar) findViewById(R.id.progressBar);
        ObjectAnimator anim = ObjectAnimator.ofInt(mprogressBar, "progress", 0, 100);
        anim.setDuration(10000);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();


        Log.d(TAG, "onCreate: Starting to create chart");
        pieChart = findViewById(R.id.PieChart);
        pieChart.setRotationEnabled(true);
        //pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(0f);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(0);
        //pieChart.setCenterText(" Chart");
        pieChart.setCenterTextSize(10);
        //pieChart.setDrawEntryLabels(true);
        //pieChart.setEntryLabelTextSize(20);
        //More options just check out the documentation!

        addDataSet();
        Resources res = getResources();
        final ProgressBar mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(100); // Secondary Progress
        mProgress.setMax(100); // Maximum Progress

/*  ObjectAnimator animation = ObjectAnimator.ofInt(mProgress, "progress", 0, 100);
animation.setDuration(50000);
animation.setInterpolator(new DecelerateInterpolator());
animation.start();*/
        tv = (TextView) findViewById(R.id.tv);
        new Thread(new Runnable() {
            @Override
            public void run() {
// TODO Auto-generated method stub
                while (pStatus < 80) {
                    pStatus += 1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
// TODO Auto-generated method stub
                            mProgress.setProgress(pStatus);
                            tv.setText(pStatus + "%");
                        }
                    });
                    try {
// Sleep for 200 milliseconds.
// Just to display the progress slowly
                        Thread.sleep(16); //thread will take approx 3 seconds to finish
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

 pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
        @Override
        public void onValueSelected(Entry e, Highlight h) {
            Log.d(TAG, "onValueSelected: Value select from chart.");
            Log.d(TAG, "onValueSelected: " + e.toString());
            Log.d(TAG, "onValueSelected: " + h.toString());

            int pos1 = e.toString().indexOf("(sum): ");
            String rating = e.toString().substring(pos1 + 7);

            for (int i = 0; i < yData.length; i++) {
                if (yData[i] == Float.parseFloat(rating)) {
                    pos1 = i;
                    break;
                }
            }
            String movie = xData[pos1 + 1];
            Toast.makeText(Main2Activity.this, "Movies " + movie + "\n" + "rating: " + rating + "K", Toast.LENGTH_LONG).show();
        }

     public List<Data> fill_with_data() {

         List<Data> data = new ArrayList<>();

         data.add(new Data( "JAN"));
         data.add(new Data( "FEB"));
         data.add(new Data( "MAR"));
         data.add(new Data( "APR"));
         data.add(new Data( "MAY"));
         data.add(new Data( "JUN"));
         data.add(new Data( "JUL"));
         data.add(new Data( "AUG"));
         data.add(new Data( "SEP"));
         data.add(new Data( "OCT"));
         data.add(new Data( "NOV"));
         data.add(new Data( "DEC"));

         return data;
     }
     public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {


         List<Data> horizontalList = Collections.emptyList();
         Context context;


         public HorizontalAdapter(List<Data> horizontalList, Context context) {
             this.horizontalList = horizontalList;
             this.context = context;
         }


         public class MyViewHolder extends RecyclerView.ViewHolder {

             TextView txtview;
             public MyViewHolder(View view) {
                 super(view);
                 txtview=(TextView) view.findViewById(R.id.txtview);
             }
         }



         @Override
         public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
             View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_menu, parent, false);

             return new MyViewHolder(itemView);
         }

         @Override
         public void onBindViewHolder(final MyViewHolder holder, final int position) {

             holder.txtview.setText(horizontalList.get(position).txt);


         }


         @Override
         public int getItemCount()
         {
             return horizontalList.size();
         }
     }

     @Override
     public void onNothingSelected() {

     }
 });
    }

    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , i));
        }

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Data");
        pieDataSet.setSliceSpace(3);
        pieDataSet.setValueTextSize(15);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.RED);

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

        mChart = findViewById(R.id.chart1);
        setData(10,100);



    }
    private void setData(int count,int range)
    {
        ArrayList<BarEntry> yvals = new ArrayList<>();
        float barwidth = 5f;
        float barspace = 9f;

        for(int i =0; i<count ;i++)
        {
            float val= (float) (Math.random()*range);
            yvals.add(new BarEntry(i*barspace,val));

        }
        BarDataSet set1;
        set1 = new BarDataSet(yvals,"Data set1");
        BarData data =new BarData(set1);
        data.setBarWidth(barwidth);
        mChart.setData(data);

        mChart.animateXY(3000,3000);
    }


}
