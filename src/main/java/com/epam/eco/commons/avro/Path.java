/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.epam.eco.commons.avro;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * @author Andrei_Tytsik
 */
public final class Path {

    public static final Pattern PATH_PATTERN = Pattern.compile("^(([^.]+)([.][^.]+)*)$");
    public static final String PATH_DELIMITER = ".";

    private final Stack<String> pathStack = new Stack<>();

    private String pathString;

    public Path() {
        this(null);
    }

    public Path(String path) {
        if (path != null) {
            append(path);
        }
    }

    public void append(String path) {
        validatePath(path);
        try {
            if (path.contains(PATH_DELIMITER)) {
                for (String token : StringUtils.split(path, PATH_DELIMITER)) {
                    pathStack.push(token);
                }
            } else {
                pathStack.push(path);
            }
        } finally {
            updatePathString();
        }
    }

    public String subtract() {
        try {
            if (pathStack.isEmpty()) {
                throw new RuntimeException("Path is empty");
            }
            return pathStack.pop();
        } finally {
            updatePathString();
        }
    }

    public boolean startsWith(Path that) {
        Validate.notNull(that, "Path is null");
        if (that.pathStack.size() > this.pathStack.size()) {
            return false;
        }
        for (int i = 0; i < that.pathStack.size(); i++) {
            String thisToken = this.pathStack.get(i);
            String thatToken = that.pathStack.get(i);
            if (!thisToken.equals(thatToken)) {
                return false;
            }
        }
        return true;
    }

    public String getPathString() {
        return pathString;
    }

    public List<String> getPathTokens() {
        return Collections.unmodifiableList(pathStack);
    }

    @Override
    public String toString() {
        return pathString;
    }

    private void updatePathString() {
        if (pathStack.isEmpty()) {
            pathString = null;
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (String token : pathStack) {
            if (builder.length() > 0) {
                builder.append(PATH_DELIMITER);
            }
            builder.append(token);
        }
        pathString = builder.toString();
    }

    private void validatePath(String path) {
        Validate.notBlank(path, "Path is blank");
        if (!PATH_PATTERN.matcher(path).matches()) {
            throw new IllegalArgumentException(String.format("Path '%s' is invalid", path));
        }
    }

}
