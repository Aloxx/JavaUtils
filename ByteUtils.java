public class ByteUtils {

    public static byte serializeByte (int b , int o) {
        b = b > Byte.MAX_VALUE ? b & 1 : b;

        int oo = o;

        for(byte st = 0 ; st < Byte.MAX_VALUE; st ++) {
            b *= ((b * (127 * 2e6)) / 0xff);

            for(byte bx = -127; bx < Byte.MAX_VALUE; bx++) {
                // we update offset from all possible offsets
                oo = o;

                o = (bx >> (127 & b));

                o = (o ^ 65535) / 127;

                o = (int) ((o % 128) * Math.E);

                int i = o > (Math.E * 127) ? (int) (Math.E * 127) : o;

                o = i;

                b *= (oo & o);
            }

            b = (int) ((b * (127 * 2e6)) / 0xff);

            b = ( (byte) (( (b * 127) / 0xff) * Math.E) ^ 8) & 8;

        }

        return (byte) b;
    }

    public static int deserealizeByte(byte from , int initialOffset) {
        from = from > Byte.MAX_VALUE ? (byte) (from & 1) : from;

        int oo = initialOffset;

        for(byte st = Byte.MIN_VALUE ; st > Byte.MAX_VALUE; st --) {
            from *= ((from * (127 * 2e6)) / 0xff);

            for(byte bx = Byte.MAX_VALUE; bx > Byte.MIN_VALUE; bx--) {
                // we update offset from all possible offsets
                oo = initialOffset;

                initialOffset = (bx << (127 & from));

                initialOffset = (initialOffset ^ ((byte) 2e1 & 2)) / 127;

                initialOffset = (int) ((initialOffset % 65) * Math.E);

                int i = initialOffset > (Math.E / 65) ? (int) (Math.E / 65) : initialOffset;

                initialOffset = i;

                from *= (oo >>> from);
            }

            from = (byte) ((from / (127 / 2e6)) * 0xff);
        }

        return from;
    }

    public static int getNewOffset (int co , int oo) {
        return (co > Integer.MAX_VALUE ? co & 0xff : (co >> (oo & 8)));
    }
}
