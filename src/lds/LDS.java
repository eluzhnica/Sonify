package lds;

import acm.program.*;
import acm.graphics.*;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class LDS extends GraphicsProgram {

    BufferedReader br;
    File poem;
    private final String ofile = "tokened.txt";

    public void run() {
        final JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(null);
        JOptionPane.showMessageDialog(this, "Select the contour image!");
        File file = fc.getSelectedFile();
        if (file == null) {
            return;
        }
        String imagePath = file.getPath();
        if (imagePath == null) {
            return;
        }
        GImage image = new GImage(imagePath);
        if (image == null) {
            return;
        }
        MidiPlayer player = new MidiPlayer();
        // this.setVisible(true);
        add(image);
        int[][] pixels = image.getPixelArray();
        int[] data = new int[pixels[0].length];
        int red, green, blue;
        MidiPlayer player1 = new MidiPlayer();
        int diam = 16;
        GOval g = new GOval(diam, diam);
        g.setFilled(true);
        g.setColor(Color.RED);
        add(g);
        int min = 2220, max = 0;
        for (int i = 0; i < pixels[0].length; i++) {
            int j;
            for (j = 0; j < pixels.length; j++) {
                red = GImage.getRed(pixels[j][i]);
                green = GImage.getGreen(pixels[j][i]);
                blue = GImage.getBlue(pixels[j][i]);
                if (red < 12 && green < 12 && blue < 12) {
                    data[i] = j;
                    if (j < min) {
                        min = j;
                    }
                    if (j > max) {
                        max = j;
                    }
                    // player.setInstrument(13);
                    // player.play(data[i]%90,50);
                    break;
                }
            }
            //	pause(pauseTime);
            //	g.setLocation(i-diam/2,j-diam/2);
        }



        //do the sonification along the moving screen


        int channel = 5; // 0 is a piano, 9 is percussion, other channels are for other instruments

        int volume = 80; // between 0 et 127
        int duration = 100; // in milliseconds
        try {
            //	try {
            //	Synthesizer synth = MidiSystem.getSynthesizer();
            //	synth.open();
            //	MidiChannel[] channels = synth.getChannels();

            // --------------------------------------
            // Play a few notes.
            // The two arguments to the noteOn() method are:
            // "MIDI note number" (pitch of the note),
            // and "velocity" (i.e., volume, or intensity).
            // Each of these arguments is between 0 and 127.
            //	for(int i=0;i<data.length;i=i+2){
            //  Thread.sleep( 500 );
            //  Thread.sleep( duration );
            //   channels[channel].noteOff( data[i] );
            //   channels[channel].noteOn( data[i]+2 , volume ); // D note
            //   Thread.sleep( duration );
            //   channels[channel].noteOff( data[i]+2  );
            // channels[channel].noteOn( 64, volume ); // E note
            // Thread.sleep( duration );
            // channels[channel].noteOff( 64 );
            //          player.setInstrument(43);
            //println(i/20+1);
            //        player.play(data[i],50);
            //      pause(50);
            // Thread.sleep( 500 );

            //              }
            // Play a C major chord.
            //        channels[channel].noteOn( data[i] %127, volume ); // C
            //      channels[channel].noteOn( data[i]+4 %127, volume ); // E
            //     channels[channel].noteOn( data[i]+6 %127, volume ); // G
            //    Thread.sleep( 3000 );
            //   channels[channel].allNotesOff();
            //  Thread.sleep( 500 );

            //              }

            //synth.close();
            //	}
            //	catch (Exception e) {
            //		e.printStackTrace();
            //	}
            JOptionPane.showMessageDialog(this, "Select the poem!");
            final JFileChooser fc1 = new JFileChooser();
            fc1.showOpenDialog(null);
            poem = fc1.getSelectedFile();
            br = new BufferedReader(new FileReader(poem));
            String line;
            String directory = ofile;
            FileWriter tokenedpoem1 = new FileWriter(directory);
            while ((line = br.readLine()) != null) {
                StringTokenizer token = new StringTokenizer(line, " ,.;'\n");
                while (token.hasMoreElements()) {
                    tokenedpoem1.append(token.nextToken());
                    tokenedpoem1.append('\n');
                }
            }
            tokenedpoem1.close();
        } catch (IOException ex) {
            Logger.getLogger(LDS.class.getName()).log(Level.SEVERE, null, ex);
        }


        FileReader tokenedpoem = null;
        try {
            tokenedpoem = new FileReader(ofile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LDS.class.getName()).log(Level.SEVERE, null, ex);
        }
        Reader rhymingwords = null;
        try {
            rhymingwords = RhymingWords.reverseOrder(RhymingWords.sort(RhymingWords.reverseOrder(tokenedpoem)));
        } catch (IOException ex) {
            Logger.getLogger(LDS.class.getName()).log(Level.SEVERE, null, ex);
        }

        BufferedReader inp = new BufferedReader(rhymingwords);
        Pair[] pairs = new Pair[1000];
        String input;
        int i = 0;
        try {
            while ((input = inp.readLine()) != null) {
                StringTokenizer tk = new StringTokenizer(input, ",.;\n");
                while (tk.hasMoreTokens()) {
                    pairs[i] = RhymingWords.find(tk.nextToken(), poem);
                    println(pairs[i].get1() + " " + pairs[i].get2());
                    i++;
                    println(i);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(LDS.class.getName()).log(Level.SEVERE, null, ex);
        }

        int[] finaldatapoem = new int[i];
        int minpoem = 1000, maxpoem = -1;
        for (int it = 0; it < i - 1; it++) {
            finaldatapoem[it] = pairs[it].distance(pairs[it + 1]);
            if (finaldatapoem[it] < minpoem) {
                minpoem = finaldatapoem[it];
            }
            if (finaldatapoem[it] > maxpoem) {
                maxpoem = finaldatapoem[it];
            }
        }

        int instrument = 43;
        int instrument2 = 3;
        int pauseTime = 150;



        for (i = 0; i < pixels[0].length; i += 10) {
            int j;
            for (j = 0; j < pixels.length; j++) {
                red = GImage.getRed(pixels[j][i]);
                green = GImage.getGreen(pixels[j][i]);
                blue = GImage.getBlue(pixels[j][i]);

                if (red < 12 && green < 12 && blue < 12) {

                    //data[i]=(data[i]-min)*80/(max-min);
                    data[i] = map_melody(data[i], max, min, 4 * 12, 2, 1);
                    player.setInstrument(instrument2);
                    player.play((data[i]) % 128, 80);
                    player1.setInstrument(43);

                    player1.play(map_melody(finaldatapoem[(i / 10) % finaldatapoem.length], minpoem, maxpoem, 4 * 12, 2, 1) % 128, 120);

                    break;
                }

            }
            pause(pauseTime);
            g.setLocation(i - diam / 2, j - diam / 2);
        }
    }

    // author of the function is Dorin Clisu.
    public int map_melody(int input, int min, int max, int base, int active_octaves, int mode) {
        // Dorin Clisu
        int black[] = {0, 2, 5, 7, 9, 12, 14, 17, 19, 21, 24, 26, 29, 31, 33, 36, 38, 41, 43, 45, 48, 50, 53, 55, 57, 60, 62, 65, 67, 69, 72, 74, 77, 79, 81, 84, 86, 89, 91, 93};
        int white[] = {0, 2, 4, 5, 7, 9, 11, 12, 14, 16, 17, 19, 21, 23, 24, 26, 28, 29, 31, 33, 35, 36, 38, 40, 41, 43, 45, 47, 48, 50, 52, 53, 55, 57, 59, 60, 62, 64, 65, 67, 69, 71, 72, 74, 76, 77, 79, 81, 83, 84, 86, 88, 89, 91, 93, 95};
        int major[] = {0, 4, 7, 12, 16, 19, 24, 28, 31, 36, 40, 43, 48, 52, 55, 60, 64, 67, 72, 76, 79, 84, 88, 91};
        int minor[] = {0, 3, 7, 12, 15, 19, 24, 27, 31, 36, 39, 43, 48, 51, 55, 60, 63, 67, 72, 75, 79, 84, 87, 91};

        int i, note = 0;
        int diff, min_diff;

        switch (mode) {
            case 4:
                note = (input - min) * active_octaves * 12 / (max - min);
                break;

            case 3:
                min_diff = Math.abs(black[0] * (max - min) - (input - min) * active_octaves * 12);
                note = 0;

                for (i = 1; i < 8 * 5; i++) {
                    diff = Math.abs(black[i] * (max - min) - (input - min) * active_octaves * 12);
                    if (diff < min_diff) {
                        min_diff = diff;
                        note = black[i];
                    }
                }
                break;

            case 2:
                min_diff = Math.abs(white[0] * (max - min) - (input - min) * active_octaves * 12);
                note = 0;

                for (i = 1; i < 8 * 7; i++) {
                    diff = Math.abs(white[i] * (max - min) - (input - min) * active_octaves * 12);
                    if (diff < min_diff) {
                        min_diff = diff;
                        note = white[i];
                    }
                }
                break;

            case 1:
                min_diff = Math.abs(major[0] * (max - min) - (input - min) * active_octaves * 12);
                note = 0;

                for (i = 1; i < 8 * 3; i++) {
                    diff = Math.abs(major[i] * (max - min) - (input - min) * active_octaves * 12);
                    if (diff < min_diff) {
                        min_diff = diff;
                        note = major[i];
                    }
                }
                break;

            case 0:
                min_diff = Math.abs(minor[0] * (max - min) - (input - min) * active_octaves * 12);
                note = 0;

                for (i = 1; i < 8 * 3; i++) {
                    diff = Math.abs(minor[i] * (max - min) - (input - min) * active_octaves * 12);
                    if (diff < min_diff) {
                        min_diff = diff;
                        note = minor[i];
                    }
                }
                break;
        }

        return base + note; ///////////////////////////////////////////
    }
}
