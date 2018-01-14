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

import info.gianlucacosta.rayon.morphology.LemmaArticle;

import java.nio.file.Path;
import java.util.regex.Pattern;

class LemmaArticleProcessor {
    private static final Pattern validArticleTitlePattern =
            Pattern.compile("(?i)[a-z][a-z-]+[a-z]");


    private static final NounProcessor nounProcessor =
            new NounProcessor();

    private static final AdjectiveProcessor adjectiveProcessor =
            new AdjectiveProcessor();


    public void process(LemmaArticle article) {
        if (article.getTitle().matches(validArticleTitlePattern.pattern())) {
            article
                    .getNouns()
                    .forEach(nounProcessor::processNoun);

            article
                    .getAdjectives()
                    .forEach(adjectiveProcessor::processAdjective);
        }
    }


    public void outputResults(Path rootOutputDirPath) {
        nounProcessor.outputResults(rootOutputDirPath);
        adjectiveProcessor.outputResults(rootOutputDirPath);
    }
}
