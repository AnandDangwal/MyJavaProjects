package com.anand.JAVA;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class JavaAudio {
    private static Clip startClip;
    private static Clip clip;
    private static Clip stopClip;
    private static AudioInputStream audioInputStream;

    private static Clip thankuClip;

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        Scanner sc = new Scanner(System.in);

        String[] songs = {"Entangled Life - Lish Grooves","In Dreams - Lish Grooves",
                            "Islandy-loop","Luna Misteriosa - Luna Cantina",
                            "Owls - Lish Grooves","Smoke - Lish Grooves","So Sweet - Lish Grooves",
                            "The Rainy Road - Lish Grooves","The-Nights-By-Avicii",
                            "Trabaja Duro Juega Duro-Luna Cantina"};

        int songNumber = chooseSong(songs);

        File file = new File("C:\\Users\\Anand Dangwal\\Music\\WAV\\"+songs[songNumber-1]+".wav");
        audioInputStream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);

        //Playing music audio.
        File startPlayingfile = new File("C:\\Users\\Anand Dangwal\\Music\\WAV\\Playing Music 2.wav");
        AudioInputStream startAudioInputStream = AudioSystem.getAudioInputStream(startPlayingfile);
        startClip = AudioSystem.getClip();
        startClip.open(startAudioInputStream);

        //Stoping music audio.
        File stopPlayingfile = new File("C:\\Users\\Anand Dangwal\\Music\\WAV\\Music Stops 1.wav");
        AudioInputStream stopAudioInputStream = AudioSystem.getAudioInputStream(stopPlayingfile);
        stopClip = AudioSystem.getClip();
        stopClip.open(stopAudioInputStream);

        //thanku for listening.
        File thankufile = new File("C:\\Users\\Anand Dangwal\\Music\\WAV\\Thanku for Listening 5.wav");
        AudioInputStream thankuAudioInputStream = AudioSystem.getAudioInputStream(thankufile);
        thankuClip = AudioSystem.getClip();
        thankuClip.open(thankuAudioInputStream);

        String response = "";

        while(!response.equals("Q")){
            System.out.println("P = Play, R = Reset, S = Stop, Q = Quit , Change the song = C");
            System.out.print("Enter your response : ");
            response = sc.next();
            response = response.toUpperCase();

            switch (response) {
                case "P" -> {
                    startClip.setMicrosecondPosition(0);
                    startClip.start();

                    startClip.addLineListener(event -> {
                        if (event.getType() == LineEvent.Type.STOP) {
                            try {
                                clip.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                case "R" -> clip.setMicrosecondPosition(0);
                case "S" -> {
                    clip.stop();
                    stopClip.setMicrosecondPosition(0);
                    stopClip.start();


                }
                case "Q" -> {
                    clip.close();

//                    thankuClip.setMicrosecondPosition(0);
                    thankuClip.start();
                    CountDownLatch latch = new CountDownLatch(1);

                    thankuClip.addLineListener(event -> {
                        if (event.getType() == LineEvent.Type.STOP) {
                            thankuClip.close();
                            System.out.println("Thanku for Listening!");
                            latch.countDown();
                        }
                    });

                    latch.await();
                }
                case "C" -> {
                    clip.close();

                    songNumber = chooseSong(songs);
                    file = new File("C:\\Users\\Anand Dangwal\\Music\\WAV\\" + songs[songNumber - 1] + ".wav");
                    audioInputStream = AudioSystem.getAudioInputStream(file);
                    clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                }
                default -> System.out.println("Invalid Response!");
            }

        }

    }

    private static int chooseSong(String[] songsList){
        Scanner sc = new Scanner(System.in);
        System.out.println("\n---------------------YOUR PLAYLIST-----------------------");
        for(int i=1; i<=songsList.length; i++){
            System.out.println(i+" : "+songsList[i-1]);
        }

        System.out.print("\nEnter the song number from above list : ");
        return sc.nextInt();
    }
}
