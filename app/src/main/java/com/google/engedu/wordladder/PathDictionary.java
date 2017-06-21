/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.wordladder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PathDictionary {
    private static final int MAX_WORD_LENGTH = 4;
    private static HashSet<String> words = new HashSet<>();

    public PathDictionary(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return;
        }
        //Log.i("Word ladder", "Loading dict");
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        //Log.i("Word ladder", "Loading dict");
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() > MAX_WORD_LENGTH) {
                continue;
            }
            words.add(word);
        }
    }

    public boolean isWord(String word) {
        return words.contains(word.toLowerCase());
    }

    private ArrayList<String> neighbours(String word) {
        ArrayList<String> neighbours = new ArrayList<>();
        char[] arr = word.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                char temp = arr[i];
                if (arr[i] != c) {
                    arr[i] = c;
                }

                String newWord = new String(arr);
                if (words.contains(newWord)) {
                    neighbours.add(newWord);
                }
                arr[i] = temp;
            }
        }
        return neighbours;
    }

    //find path from start to end
    public String[] findPath(String start, String end) {

        //for each ladder has its own path, this is the init path
        List<String> path = new ArrayList<>();
        path.add(start);

        //and the init ladder to queue
        ArrayDeque<Ladder> queue = new ArrayDeque<>();
        queue.add(new Ladder(path, 1, start));

        //in case a word is added to queue for multiple times
        Set<String> visited = new HashSet<>();
        visited.add(start);
        Ladder finallLadder = null;
        while (queue.peek() != null) {
            Ladder ladder = queue.poll();

            // when the end word is reached, return
            if (end.equals(ladder.getLastWord())) {
                finallLadder = ladder;
                break;
            }

            //other wise, find all the neighbour of the current ladder
            List<String> neighbours = neighbours(ladder.getLastWord());

            //for each neighbour, create a new ladder, increase the length, and add to queue
            for (String neighbour : neighbours) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    List<String> newPath = new ArrayList<>(ladder.getPath());
                    newPath.add(neighbour);

                    Ladder newLadder = new Ladder(newPath, ladder.getLength()+1, neighbour);
                    queue.add(newLadder);
                }
            }
        }
        return finallLadder == null ? null : finallLadder.getPath().toArray(new String[finallLadder.getPath().size()]);
    }
}
