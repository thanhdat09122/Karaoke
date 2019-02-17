package com.example.mypc.karaoke;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.mypc.adapter.MusicAdapter;
import com.example.mypc.model.Music;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ListView lvSongs;
    public static ArrayList<Music> arraySongs;
    MusicAdapter adapterSongs;

    ListView lvFavorite;
    ArrayList<Music> arrayFavorite;
    MusicAdapter adapterFavorite;

    TabHost tabHost;

    public static String DATABASE_NAME = "Arirang.sqlite";
    private static  final String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SolveCopyAssetsToMoblie();

        addControls();
        addEvents();

        SolveSongs();
    }

    private void addEvents() {
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId.equalsIgnoreCase("t1"))
                {
                    SolveSongs();

                }else if(tabId.equalsIgnoreCase("t2"))
                {
                    SolveFavorite();
                }
            }
        });
    }

    private void SolveFavorite() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = database.query("ArirangSongList",null,
                "YEUTHICH=?",new String[]{"1"},
                null,null,null);
        arrayFavorite.clear();
        while (cursor.moveToNext()) {
            String mabh  = cursor.getString(0);
            String tenbh = cursor.getString(1);
            String casi  = cursor.getString(3);
            int yeuthich = cursor.getInt(5);

            Music music = new Music();
            music.setMa(mabh);
            music.setTen(tenbh);
            music.setCasi(casi);
            music.setThich(yeuthich==1);
            arrayFavorite.add(music);
        }
        cursor.close();
        adapterFavorite.notifyDataSetChanged();


        arrayFavorite.clear();
        for(Music music: arraySongs)
        {
            if(music.isThich())
                arrayFavorite.add(music);
        }
        adapterFavorite.notifyDataSetChanged();
    }

    private void SolveSongs() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = database.query("ArirangSongList",null,null,null,null,null,null);
        arraySongs.clear();
        while (cursor.moveToNext()) {
            String mabh  = cursor.getString(0);
            String tenbh = cursor.getString(1);
            String casi  = cursor.getString(3);
            int yeuthich = cursor.getInt(5);

            Music music = new Music();
            music.setMa(mabh);
            music.setTen(tenbh);
            music.setCasi(casi);
            music.setThich(yeuthich==1);
            arraySongs.add(music);
        }
        cursor.close();
        adapterSongs.notifyDataSetChanged();
    }

    private void addControls() {
        tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("",getResources().getDrawable(R.drawable.song4));
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("",getResources().getDrawable(R.drawable.favorite6));
        tabHost.addTab(tab2);

        lvSongs = (ListView) findViewById(R.id.lvSongs);
        arraySongs = new ArrayList<>();
        adapterSongs = new MusicAdapter(this,R.layout.item, arraySongs);
        lvSongs.setAdapter(adapterSongs);

        lvFavorite = (ListView) findViewById(R.id.lvFavorite);
        arrayFavorite = new ArrayList<>();
        adapterFavorite = new MusicAdapter(this,R.layout.item, arrayFavorite);
        lvFavorite.setAdapter(adapterFavorite);

//        SongSimulator();
    }

    private void SongSimulator() {
        arraySongs.add(new Music("11111","Only Love","TradeMark",false));
        arraySongs.add(new Music("22222","See You Again","Charlie Puth",false));
        arraySongs.add(new Music("33333","How Long","Charlie Puth",false));
        arraySongs.add(new Music("44444","Attention","Charlie Puth",false));
        adapterSongs.notifyDataSetChanged();
    }
    private void SolveCopyAssetsToMoblie() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if(!dbFile.exists()){
            try
            {
                CopyDatabaseFromAsset();
                Toast.makeText(this, "Copy CSDL To System Successfully", Toast.LENGTH_SHORT).show();
            }
            catch (Exception ex)
            {
                Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void CopyDatabaseFromAsset() {
        try
        {
            InputStream inputStream =  getAssets().open(DATABASE_NAME);

            //Path to just created empty db
            String outFileName = getDatabasePath();

            //if the path doesn't exist first, create it
            File file = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if(!file.exists())
            {
                file.mkdir();
            }
            //Open the empty db as the output stream
            OutputStream myOutPut = new FileOutputStream(outFileName);

            // Transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while((length = inputStream.read(buffer)) > 0)
            {
                myOutPut.write(buffer, 0, length);
            }

            //Close the streams
            myOutPut.flush();
            myOutPut.close();
            inputStream.close();
        }
        catch (Exception ex)
        {
            Log.e("Error", ex.toString());
        }
    }

    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_search,menu);
//        MenuItem menuItem = menu.findItem(R.id.menuSearch);
//        SearchView searchView = (SearchView) menuItem.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if(TextUtils.isEmpty(newText)){
//                    adapterSongs.filter("");
//                    lvSongs.clearTextFilter();
//                }else{
//                    adapterSongs.filter(newText);
//                }
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }

}
