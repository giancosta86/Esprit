/*^
  ===========================================================================
  Esprit
  ===========================================================================
  Copyright (C) 2018 Gianluca Costa
  ===========================================================================
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ===========================================================================
*/

package info.gianlucacosta.esprit.util;

import java.util.concurrent.atomic.AtomicLong;

public class ItemsTracker {
    private static final String batchOutput = "*";
    private static final long viewportCharWidth = 80;

    private final AtomicLong itemsCounter =
            new AtomicLong();

    private final long batchSize;


    public ItemsTracker(long batchSize) {
        this.batchSize = batchSize;
    }


    public Long count() {
        return itemsCounter.get();
    }


    public void trackItem() {
        long itemsCount =
                itemsCounter.incrementAndGet();

        if (itemsCount % (batchSize * viewportCharWidth) == 0) {
            System.out.println(batchOutput);
        } else if (itemsCount % batchSize == 0) {
            System.out.print(batchOutput);
        }
    }
}
