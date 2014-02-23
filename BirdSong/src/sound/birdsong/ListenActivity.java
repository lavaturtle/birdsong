package sound.birdsong;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class ListenActivity extends Activity
{
	private AudioRecord _recorder;
	private static final int _sampleRateHz = 44100;
	private static final int _channelConfig = AudioFormat.CHANNEL_IN_MONO;
	private static final int _audioFormat = AudioFormat.ENCODING_PCM_16BIT;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listen);
		
		// Start recording audio
		int bufferSize = AudioRecord.getMinBufferSize(_sampleRateHz, _channelConfig, _audioFormat);
		try
		{
			_recorder = new AudioRecord(MediaRecorder.AudioSource.DEFAULT, _sampleRateHz, _channelConfig, _audioFormat, bufferSize);
			_recorder.startRecording();
		}
		catch (RuntimeException ex)
		{
			TextView topAnswerTxt = (TextView) findViewById(sound.birdsong.R.id.listeningTitle);
			topAnswerTxt.setText("Could not configure audio");
			// TODO: replace analyze button with back to home button
		}
		// TODO: Start a thread to poll _recorder and append the data to a file 
		
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	public void endListen(View view)
	{
		_recorder.stop();
		Intent intent = new Intent(this, AnalysisActivity.class);
		// TODO: send the filepath we wrote data to
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.listen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
