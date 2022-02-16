package com.portal.full.appmozoou;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class LearningEnglishMain extends AppCompatActivity
        implements ListView.OnItemClickListener {

    static final String KEY_TAG = "weatherdata"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_CITY = "city";
    static final String KEY_TEMP_C = "tempc";
    static final String KEY_TEMP_F = "tempf";
    static final String KEY_CONDN = "condition";
    static final String KEY_SPEED = "windspeed";
    static final String KEY_ICON = "icon";
    ListView list;
    BinderData adapter = null;
    List<HashMap<String,String>> weatherDataCollection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

                try {


                    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                    Document doc = docBuilder.parse(getAssets().open("learningenglish.xml"));

                    weatherDataCollection = new ArrayList<HashMap<String, String>>();

                    // normalize text representation
                    doc.getDocumentElement().normalize();

                    NodeList weatherList = doc.getElementsByTagName("weatherdata");

                    HashMap<String, String> map = null;

                    for (int i = 0; i < weatherList.getLength(); i++) {

                        map = new HashMap<String, String>();

                        Node firstWeatherNode = weatherList.item(i);

                        if (firstWeatherNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element firstWeatherElement = (Element) firstWeatherNode;
                            //-------
                            NodeList idList = firstWeatherElement.getElementsByTagName(KEY_ID);
                            Element firstIdElement = (Element) idList.item(0);
                            NodeList textIdList = firstIdElement.getChildNodes();
                            //--id
                            map.put(KEY_ID, ((Node) textIdList.item(0)).getNodeValue().trim());

                            //2.-------
                            NodeList cityList = firstWeatherElement.getElementsByTagName(KEY_CITY);
                            Element firstCityElement = (Element) cityList.item(0);
                            NodeList textCityList = firstCityElement.getChildNodes();
                            //--city
                            map.put(KEY_CITY, ((Node) textCityList.item(0)).getNodeValue().trim());

                            //3.-------
                            NodeList tempList = firstWeatherElement.getElementsByTagName(KEY_TEMP_C);
                            Element firstTempElement = (Element) tempList.item(0);
                            NodeList textTempList = firstTempElement.getChildNodes();
                            //--city
                            map.put(KEY_TEMP_C, ((Node) textTempList.item(0)).getNodeValue().trim());

                            //4.-------
                            NodeList condList = firstWeatherElement.getElementsByTagName(KEY_CONDN);
                            Element firstCondElement = (Element) condList.item(0);
                            NodeList textCondList = firstCondElement.getChildNodes();
                            //--city
                            map.put(KEY_CONDN, ((Node) textCondList.item(0)).getNodeValue().trim());

                            //5.-------
                            NodeList speedList = firstWeatherElement.getElementsByTagName(KEY_SPEED);
                            Element firstSpeedElement = (Element) speedList.item(0);
                            NodeList textSpeedList = firstSpeedElement.getChildNodes();
                            //--city
                            map.put(KEY_SPEED, ((Node) textSpeedList.item(0)).getNodeValue().trim());

                            //6.-------
                            NodeList iconList = firstWeatherElement.getElementsByTagName(KEY_ICON);
                            Element firstIconElement = (Element) iconList.item(0);
                            NodeList textIconList = firstIconElement.getChildNodes();
                            //--city
                            map.put(KEY_ICON, ((Node) textIconList.item(0)).getNodeValue().trim());

                            //Add to the Arraylist
                            weatherDataCollection.add(map);
                        }
                    }


                    BinderData bindingData = new BinderData(this, weatherDataCollection);


                    list = (ListView) findViewById(R.id.list);

                    Log.i("BEFORE", "<<------------- Before SetAdapter-------------->>");

                    list.setAdapter(bindingData);

                    Log.i("AFTER", "<<------------- After SetAdapter-------------->>");
                    list.setOnItemClickListener(this);
                    // Click event for single list row
         /*   list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Intent i = new Intent();
                    i.setClass(Main.this, SampleActivity.class);

                    // parameters
                    i.putExtra("position", String.valueOf(position + 1));

					*//* selected item parameters
					 * 1.	City name
					 * 2.	Weather
					 * 3.	Wind speed
					 * 4.	Temperature
					 * 5.	Weather icon
					 *//*
                    i.putExtra("city", weatherDataCollection.get(position).get(KEY_CITY));
                    i.putExtra("weather", weatherDataCollection.get(position).get(KEY_CONDN));
                    i.putExtra("windspeed", weatherDataCollection.get(position).get(KEY_SPEED));
                    i.putExtra("temperature", weatherDataCollection.get(position).get(KEY_TEMP_C));
                    i.putExtra("icon", weatherDataCollection.get(position).get(KEY_ICON));

                    // start the sample activity
                    startActivity(i);
                }
            });
*/
                } catch (IOException ex) {
                    Log.e("Error", ex.getMessage());
                } catch (Exception ex) {
                    Log.e("Error", "Loading exception");
                }

            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
     /*   getMenuInflater().inflate(R.menu.main, menu);

        MenuItem playButton = menu.findItem(R.id.action);
        playButton.setVisible(false);
*/

        return true;


    }





    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



        // parameters


        if (weatherDataCollection.get(position).get(KEY_ID).equals("1")){
            Intent newActivity = new Intent(LearningEnglishMain.this, Grammar.class);

            startActivity(newActivity);

        }else if (weatherDataCollection.get(position).get(KEY_ID).equals("2")) {
            Intent newActivity = new Intent(LearningEnglishMain.this, Words.class);

            startActivity(newActivity);
        }else if (weatherDataCollection.get(position).get(KEY_ID).equals("3")) {
            Intent newActivity = new Intent(LearningEnglishMain.this, Conversation.class);

            startActivity(newActivity);
        }else if (weatherDataCollection.get(position).get(KEY_ID).equals("4")) {
            Intent newActivity = new Intent(LearningEnglishMain.this, Book.class);

            startActivity(newActivity);
        }else if (weatherDataCollection.get(position).get(KEY_ID).equals("5")) {
            Intent newActivity = new Intent(LearningEnglishMain.this, EnNews.class);

            startActivity(newActivity);
        }else if (weatherDataCollection.get(position).get(KEY_ID).equals("6")) {
            Intent newActivity = new Intent(LearningEnglishMain.this, Story.class);

            startActivity(newActivity);
        }else if (weatherDataCollection.get(position).get(KEY_ID).equals("7")) {
            Intent newActivity = new Intent(LearningEnglishMain.this, EnPoetry.class);

            startActivity(newActivity);
        }



    }

}
