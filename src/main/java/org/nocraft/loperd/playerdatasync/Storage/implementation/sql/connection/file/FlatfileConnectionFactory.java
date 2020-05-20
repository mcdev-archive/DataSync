/*
 * This file is part of LuckPerms, licensed under the MIT License.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package org.nocraft.loperd.playerdatasync.Storage.implementation.sql.connection.file;

import org.nocraft.loperd.playerdatasync.NoPlayerDataSync;
import org.nocraft.loperd.playerdatasync.Storage.implementation.sql.connection.ConnectionFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

abstract class FlatfileConnectionFactory implements ConnectionFactory {
    protected static final DecimalFormat DF = new DecimalFormat("#.##");

    protected final Path file;

    FlatfileConnectionFactory(Path file) {
        this.file = file;
    }

    @Override
    public void init(NoPlayerDataSync plugin) {

    }

    protected Path getWriteFile() {
        return this.file;
    }

    @Override
    public Map<String, String> getMeta() {
        Map<String, String> meta = new LinkedHashMap<>();

        Path databaseFile = getWriteFile();
        if (Files.exists(databaseFile)) {
            long length;
            try {
                length = Files.size(databaseFile);
            } catch (IOException e) {
                length = 0;
            }

            double size = length / 1048576D;
            meta.put("File Size", DF.format(size) + "MB");
        } else {
            meta.put("File Size", "0MB");
        }

        return meta;
    }
}
