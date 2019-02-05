
package GameEngine.Sound;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
            
    private Clip clip;
    private FloatControl gainControl;
    
    public Sound(String path){
        try {
            InputStream audioSrc = Sound.class.getResourceAsStream(path);
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED, 
                    baseFormat.getSampleRate(), 
                    16, 
                    baseFormat.getChannels(), 
                    baseFormat.getChannels()*2,
                    baseFormat.getSampleRate(),
                    false
                    );
            
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(dais);
            
            gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void play(){
        if(clip==null) return;
        
        stop();
        clip.setFramePosition(0);
        
        while(!clip.isRunning())
            clip.start();
    }
    
    public void stop(){
        if(clip.isRunning())
            clip.stop();
    }
    
    public void loop(int n){
        clip.setFramePosition(n);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void adjustVolume(float value) {
        gainControl.setValue(value);
    }
    
    public void close() {
        stop();
        clip.drain();
        clip.close();
    }
    
    public boolean isRunning(){
        return clip.isRunning();
    }
    
}
