/*
 * Copyright (C) 2012 uPhyca Inc.
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
package com.uphyca.robots.core;

/**
 * 
 * RS303MR/RS304MD 取扱説明書 Ver1.13 より.
 * 
 * サーボに対して、メモリーマップのデータを送信するときに使用するパケットです。
 * 
 * <dl>
 * 
 * <dt>パケット構成</dt>
 * <dd>[Header][ID][Flag][Address][Length][Count][Data][Sum]</dd>
 * 
 * <dt>Header</dt>
 * <dd>パケットの先頭を表します。ショートパケットではFAAFに設定します。</dd>
 * 
 * <dt>ID</dt>
 * <dd>サーボのIDです。1〜127(01H〜7FH)までの値が使用できます。
 * ID:255を指定すると、全IDのサーボへの共通指令になります(リターンデータは取れません)。</dd>
 * 
 * <dt>Flag</dt>
 * <dd>saー簿からのリターンデータ取得やデータ書き込み時の設定をします。</dd>
 * 
 * <dt>Address</dt>
 * <dd>メモリーマップ上のアドレスを指定します。 このアドレスから「Length」に指定した長さ分のデータをメモリーマップに書き込みます。</dd>
 * 
 * <dt>Length</dt>
 * <dd>データ1ブロックの長さを指定します。ショートパケットでは[Data]のバイト数になります。</dd>
 * 
 * <dt>Count</dt>
 * <dd>サーボの数を表します。ショートパケットでメモリーマップに書き込む時は1に設定します。</dd>
 * 
 * <dt>Data</dt>
 * <dd>メモリーマップに書き込むデータです。</dd>
 * 
 * <dt>Sum</dt>
 * <dd>送信データの確認用のチェックサムで、パケットの[ID]から[Data]の末尾までを1バイトずつXORした値を指定します。
 * 
 * 例)次の送信データのチェックサムは、次のようになります。</dd>
 * 
 * 
 * <table>
 * <thead>
 * <tr>
 * <th>Hdr</th>
 * <th>ID</th>
 * <th>Flg</th>
 * <th>Adr</th>
 * <th>Len</th>
 * <th>Cnt</th>
 * <th>Dat</th>
 * <th>Sum</th>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>FA AF</td>
 * <td>01</td>
 * <td>00</td>
 * <td>1E</td>
 * <td>02</td>
 * <td>01</td>
 * <td>00 00</td>
 * <td>1C</td>
 * </tr>
 * <tbody>
 * </table>
 * 
 * <pre>
 * 01H XOR 00H XOR 1EH XOR 02H XOR 01H XOR 00H XOR 00H = 1C
 * </pre>
 * 
 * </dl>
 * 
 * <dt>Flag</dt> <dd>Flagはビット毎に下記表のような意味があります。
 * 
 * <table>
 * <thead>
 * <tr>
 * <th>ビット</th>
 * <th>機能</th>
 * </tr>
 * </thead> <tbody> </tbody>
 * <tr>
 * <td>7</td>
 * <td>未使用</td>
 * </tr>
 * <tr>
 * <td>6</td>
 * <td>フラッシュROMへ書き込み</td>
 * </tr>
 * <tr>
 * <td>5</td>
 * <td>サーボを再起動</td>
 * </tr>
 * <tr>
 * <td>4</td>
 * <td>メモリーマップの値を初期値に戻す</td>
 * </tr>
 * <tr>
 * <td>3</td>
 * <td>リターンパケットのアドレス指定</td>
 * </tr>
 * <tr>
 * <td>2</td>
 * <td>リターンパケットのアドレス指定</td>
 * </tr>
 * <tr>
 * <td>1</td>
 * <td>リターンパケットのアドレス指定</td>
 * </tr>
 * <tr>
 * <td>0</td>
 * <td>リターンパケットのアドレス指定</td>
 * </tr>
 * </table>
 * </dd>
 * 
 * TODO 続き書く.
 * 
 */
public class ShortPacket extends Packet {

    public static final class Builder {
        private final ShortPacket packet = new ShortPacket();

        private Builder() {
        }

        public ShortPacket.Builder id(byte newId) {
            packet.setId(newId);
            return this;
        }

        public ShortPacket.Builder id(int newId) {
            return id(Bytes.of(newId));
        }

        public ShortPacket.Builder flag(byte newFlag) {
            packet.setFlag(newFlag);
            return this;
        }

        public ShortPacket.Builder flag(int newFlag) {
            return flag(Bytes.of(newFlag));
        }

        public ShortPacket.Builder flag(boolean... newFlag) {
            return flag(Bytes.of(newFlag));
        }

        public ShortPacket.Builder address(byte newAddress) {
            packet.setAddress(newAddress);
            return this;
        }

        public ShortPacket.Builder address(int newAddress) {
            return address(Bytes.of(newAddress));
        }

        /**
         * このショートパケットのLengthフィールドを設定する.
         * 
         * LengthフィールドはDataフィールドの値から自動的に計算されるが、このメソッドを使って明示的に設定した場合は、
         * このメソッドの呼び出しで設定されたLengthを使う.
         * 
         * @param newLength
         * @return
         */
        public ShortPacket.Builder length(byte newLength) {
            packet.setLength(newLength);
            packet.setLengthSet(true);
            return this;
        }

        /**
         * このショートパケットのLengthフィールドを設定する.
         * 
         * LengthフィールドはDataフィールドの値から自動的に計算されるが、このメソッドを使って明示的に設定した場合は、
         * このメソッドの呼び出しで設定されたLengthを使う.
         * 
         * @param newLength
         * @return
         */
        public ShortPacket.Builder length(int newLength) {
            return length(Bytes.of(newLength));
        }

        public ShortPacket.Builder count(byte newCount) {
            packet.setCount(newCount);
            return this;
        }

        public ShortPacket.Builder count(int newCount) {
            return count(Bytes.of(newCount));
        }

        public ShortPacket.Builder data(byte[] newData) {
            packet.data()
                  .add(RawPacket.data(newData));
            return this;
        }

        public ShortPacket.Builder data(int... newData) {
            return data(Bytes.of(newData));
        }

        public ShortPacket build() {
            return packet;
        }
    }

    private static ShortPacket.Builder builder() {
        return new ShortPacket.Builder();
    }

    ShortPacket() {
        setHeader(Bytes.of(0xFA, 0xAF));
    }

    public static ShortPacket.Builder id(byte newId) {
        return builder().id(newId);
    }

    public static ShortPacket.Builder id(int newId) {
        return builder().id(newId);
    }

    public static ShortPacket.Builder flag(byte newFlag) {
        return builder().flag(newFlag);
    }

    public static ShortPacket.Builder flag(boolean... newFlag) {
        return builder().flag(newFlag);
    }

    public static ShortPacket.Builder flag(int newFlag) {
        return builder().flag(newFlag);
    }

    public static ShortPacket.Builder address(byte newAddress) {
        return builder().address(newAddress);
    }

    public static ShortPacket.Builder address(int newAddress) {
        return builder().address(newAddress);
    }

    public static ShortPacket.Builder length(byte newLength) {
        return builder().length(newLength);
    }

    public static ShortPacket.Builder length(int newLength) {
        return builder().length(newLength);
    }

    public static ShortPacket.Builder count(byte newCount) {
        return builder().count(newCount);
    }

    public static ShortPacket.Builder count(int newCount) {
        return builder().count(newCount);
    }

    public static ShortPacket.Builder data(byte[] newData) {
        return builder().data(newData);
    }

    public static ShortPacket.Builder data(int... newData) {
        return builder().data(newData);
    }
}
