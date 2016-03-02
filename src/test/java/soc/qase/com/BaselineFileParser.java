package soc.qase.com;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Just a helper class used to determine all existent variants of baseline message send by server to client.
 *
 * Created by Vojtech.Smital on 1.3.2016.
 */
public class BaselineFileParser {

    private static final String FILE_ENCODING = "UTF-8";
    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineFileParser.class);

    private Map<String, BaselineType> baselineMapping = new HashMap();
    private int counter = 0;


    public void parse(final String baselineFilePath, final String outputFilePath) throws IOException {
        final List<String> lines = FileUtils.readLines(new File(baselineFilePath), FILE_ENCODING);
        if (lines == null || lines.isEmpty()) {
            LOGGER.warn("No lines found!");
            return;
        }

        for (String line : lines) {
            parseLine(line);
            counter++;
        }

        FileUtils.writeLines(new File(outputFilePath), baselineMapping.values(), "\n");
    }

    public static void main(String[] args) {
        try {
            final BaselineFileParser baselineFileParser = new BaselineFileParser();
            baselineFileParser.parse("c:\\Users\\smital\\DAI_TOOLS\\quake_baselines_hex.txt", "c:\\Users\\smital\\DAI_TOOLS\\quake_baselines_parsed_output.txt");
            LOGGER.info("Parsing finished");
        } catch (IOException ioe) {
            LOGGER.error("Error occured", ioe);
        }
    }

    private void parseLine(final String line) {
        if (!line.startsWith("0e")) {
            return;
        }


        final String bitMask = line.substring(0, 15);
        final int packetLength = line.replaceAll(" ", "").length();
        if (baselineMapping.containsKey(bitMask)) {
            if (lengthAlreadyDefined(baselineMapping.get(bitMask), packetLength)) {
                return;
            } else {
                baselineMapping.get(bitMask).getPacketLengths().add(packetLength);
            }
        } else {
            baselineMapping.put(bitMask, new BaselineType(bitMask, packetLength));
        }
    }

    private boolean lengthAlreadyDefined(final BaselineType baselineType, final int length) {
        try {
            final Integer foundNumber = Iterables.find(baselineType.getPacketLengths(), new Predicate<Integer>() {
                public boolean apply(final Integer integer) {
                    return integer == length;
                }
            });

            return true;
        } catch (NoSuchElementException nsee) {
            return false;
        }
    }

    private class BaselineType {
        private String baselineHexPrefix;
        private List<Integer> packetLengths;

        BaselineType(final String baselineHexPrefix, final int packetLength) {
            this.baselineHexPrefix = baselineHexPrefix;
            this.packetLengths = new ArrayList();
            packetLengths.add(packetLength);
        }

        public String getBaselineHexPrefix() {
            return baselineHexPrefix;
        }

        public void setBaselineHexPrefix(final String baselineHexPrefix) {
            this.baselineHexPrefix = baselineHexPrefix;
        }

        public List<Integer> getPacketLengths() {
            return packetLengths;
        }

        public void setPacketLengths(final List<Integer> packetLengths) {
            this.packetLengths = packetLengths;
        }

        @Override
        public String toString() {
            return baselineHexPrefix + ": " + StringUtils.join(packetLengths, ",");
        }
    }

}
