package asu.reach;

import java.util.Calendar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class TimePreferenceActivity extends PreferenceActivity {
  private Bundle savedInstanceState;
  
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.savedInstanceState = savedInstanceState;
    addPreferencesFromResource(R.xml.prefs);
  }
  
  // This is used by the test.
  public Bundle getBundle() {
    return savedInstanceState;
  }
  
  // This is used by the test.
  public Calendar getCalendarOfAlert() {
    return TimePreference.getTimeFor(preferences(),"notif_at");
  }
  
  // This is used by the test.
  public Calendar getCalendarOfWaking() {
    return TimePreference.getTimeFor(preferences(),"wake_up");
  }
  
  private SharedPreferences preferences() {
    return PreferenceManager.getDefaultSharedPreferences(this);
  }
  
  @Override
  protected void onResume() {
    super.onResume();
    
    SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
    getPreference("wake_up").setTime(prefs.getString("wake_up", TimePreference.defaultCalendarString()));
  }
  
  @Override
  protected void onPause() {
    super.onPause();
    
    SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
    editor.putString("notif_at", TimePreference.formatter().format(getCalendarOfAlert().getTime()));
    editor.commit();
  }
  
  public void clearSharedPreferences() {
    SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
    editor.clear();
    editor.commit();
  }
    
  
  private TimePreference getPreference(String key) {
    return (TimePreference)getPreferenceManager().findPreference(key);
  }
}
