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

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
        String line = null;
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

    public String[] findPath(String start, String end) {
        List<String> path = new ArrayList<>();
        path.add(start);

        ArrayDeque<Ladder> queue = new ArrayDeque<>();
        queue.add(new Ladder(path, 1, start));

        Set<String> visited = new HashSet<>();
        visited.add(start);

        while (queue.peek() != null) {
            Ladder ladder = queue.poll();

            if (end.equals(ladder.getLastWord())) {
                return ladder.getPath().toArray(new String[ladder.getPath().size()]);
            }

            List<String> neighbours = neighbours(ladder.getLastWord());
            for (String neighbour : neighbours) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    List<String> newPath = new ArrayList<>(ladder.getPath());
                    newPath.add(neighbour);

                    Ladder newLadder = new Ladder(newPath, 1, neighbour);
                    queue.add(newLadder);
                }
            }
        }

        //this code will never be executed
        if (!queue.isEmpty()) {
            Ladder l = queue.peek();
            List<String> p = l.getPath();
            return p.toArray(new String[p.size()]);
        }
        return null;
    }
}
