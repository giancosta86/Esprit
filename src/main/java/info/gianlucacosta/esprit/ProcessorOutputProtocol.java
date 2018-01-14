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

package info.gianlucacosta.esprit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import info.gianlucacosta.esprit.outputformat.BasicFrequencyOutput;
import info.gianlucacosta.rayon.statistics.FrequencyTracker;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class ProcessorOutputProtocol {
    private ProcessorOutputProtocol() {
    }

    public static void printProcessorHeader(String header) {
        System.out.println();
        System.out.println();

        System.out.println(
                String.format(
                        "== %s ==",
                        header
                )
        );

        System.out.println();
    }


    public static void saveActivityResult(
            String activityTitle,
            Path outputDirPath,
            String outputFileName,
            Supplier<Object> resultSupplier
    ) {
        System.out.print(
                String.format(
                        "* %s... ",
                        activityTitle
                )
        );

        Path outputPath =
                outputDirPath.resolve(outputFileName);


        Object result =
                resultSupplier.get();


        saveResultToFile(outputPath, result);

        System.out.println("OK");
    }


    private static void saveResultToFile(Path outputFilePath, Object result) {
        Gson gson =
                new GsonBuilder().setPrettyPrinting().create();

        String jsonString =
                gson.toJson(result);

        try {
            Files.createDirectories(
                    outputFilePath.getParent()
            );

            Files.write(
                    outputFilePath,
                    jsonString.getBytes(Charset.forName("utf-8"))
            );
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    public static void saveFrequencyActivityResult(
            String activityTitle,
            Path outputDirPath,
            String outputFileName,
            FrequencyTracker<?> frequencyTracker,
            long minimumFrequencyConsidered
    ) {
        frequencyTracker.filterFrequencies(minimumFrequencyConsidered);

        saveActivityResult(
                activityTitle,
                outputDirPath,
                outputFileName,
                () ->
                        StreamSupport.stream(
                                frequencyTracker.spliterator(),
                                false
                        )
                                .map(transformPair -> {
                                    Object item =
                                            transformPair.getKey();

                                    long frequency =
                                            transformPair.getValue();

                                    return new BasicFrequencyOutput(
                                            item,
                                            frequency
                                    );
                                })
                                .collect(
                                        Collectors.toList()
                                )

        );
    }
}
