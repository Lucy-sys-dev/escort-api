package io.ssnc.ac.escort.util

import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetSocketAddress
import java.net.SocketException
import java.nio.charset.Charset

object UdpHelper {
    /**
     * UDP 수신
     *
     * @param port
     * @param listener
     */
    fun receiveUdp(port: Int, listener: OnUdpReceiveListener) {
        object : Thread() {
            override fun run() {
                var socket: DatagramSocket? = null
                try {
                    socket = DatagramSocket(port)
                } catch (e: SocketException) {
                    e.printStackTrace()
                }

                val receBuf = ByteArray(1024)
                val packet = DatagramPacket(receBuf, receBuf.size)
                while (!listener.isStop) {
                    try {
                        socket!!.receive(packet)
                        val receive = String(packet.data, 0, packet.length, Charset.forName("utf-8"))
                        listener.receive(receive)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
        }.start()
    }

    /**
     * 브로드 캐스트 보내기
     *
     * @param ip
     * @param port
     * @param content
     */
    fun sendUdp(ip: String, port: Int, content: String) {
        object : Thread() {
            private var sendBuf: ByteArray? = null

            override fun run() {
                try {
                    sendBuf = content.toByteArray(charset("utf-8"))
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }

                val recvPacket =
                    DatagramPacket(sendBuf, sendBuf!!.size, InetSocketAddress(ip, port))
                try {
                    val socket = DatagramSocket()
                    socket.send(recvPacket)
                    socket.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }.start()
    }

    interface OnUdpReceiveListener {

        val isStop: Boolean
        fun receive(result: String)
    }
}