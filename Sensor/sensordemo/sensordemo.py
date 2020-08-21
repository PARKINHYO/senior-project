import socket, gps, time, threading, sys, smbus, math
from serial import Serial

global gpsData
global step_flag
global f
global sum_step


def fileHanding():
    global f
    global gpsData
    try:
        sample = gpsData + ", " + str(sum_step)
        f = open("/home/pi/sensor.txt", 'w')
        f.write(sample)
        f.close()
    except NameError:
        pass
    threading.Timer(0.05, fileHanding).start()


def SndData():
    global gpsData
    global step_flag

    soc = socket.socket()
    host = "127.0.0.1"
    port = 9999
    soc.bind((host, port))
    soc.listen(5)

    while True:
        conn = None
        try:
            conn, addr = soc.accept()
            print("Got connection from", addr)
            length_of_message = int.from_bytes(conn.recv(2), byteorder='big')
            msg = conn.recv(length_of_message).decode("UTF-8")
            print(msg)
            print(length_of_message)

            # Note the corrected indentation below
            if "Hello" in msg:
                message_to_send = (gpsData + ", " + str(step_flag)).encode("UTF-8")
                # message_to_send = gpsData.encode("UTF-8")
                # message_to_send = str(step_flag).encode("UTF-8")
                conn.send(len(message_to_send).to_bytes(2, byteorder='big'))
                conn.send(message_to_send)
            else:
                print("no message")
        except KeyboardInterrupt:
            if conn:
                conn.close()
                soc.close()


def GpsSensor():
    global sum_step
    global gpsData
    global f
    try:
        sum_step += 1
        gpsd = gps.gps()
        gpsd.stream(gps.WATCH_ENABLE | gps.WATCH_NEWSTYLE)

        for report in gpsd:
            if report['class'] == 'TPV':
                print("gps : %.6f, %.6f" % (report['lat'], report['lon']))
                lat = "{0:.6f}".format(report['lat'])
                lon = "{0:.6f}".format(report['lon'])
                gpsData = lat + ", " + lon
                break
    except:
        print("GPS Loading.....")
        pass

    threading.Timer(5, GpsSensor).start()


class Gyro:

    def __init__(self):
        # register variable
        self.power_mgmt_1 = 0x6b
        self.power_mgmt_2 = 0x6c
        # self.step_flag = 0  # working flag
        # self.sum_step = 0  # work count
        self.bus = smbus.SMBus(1)  # raspberry3,4 is 1   raspberry1 is 0
        self.address = 0x68  # i2c detect config
        self.bus.write_byte_data(self.address, self.power_mgmt_1, 0)  # mpu6050 on

    def read_byte(self, adr):
        return self.bus.read_byte_data(self.address, adr)

    def read_word(self, adr):
        high = self.bus.read_byte_data(self.address, adr)
        low = self.bus.read_byte_data(self.address, adr + 1)
        val = (high << 8) + low
        return val

    def read_word_2c(self, adr):
        val = self.read_word(adr)
        if (val >= 0x8000):
            return -((65535 - val) + 1)
        else:
            return val

    def dist(self, a, b):
        return math.sqrt((a * a) + (b * b))

    def get_y_rotation(self, x, y, z):
        radians = math.atan2(x, self.dist(y, z))
        return -math.degrees(radians)

    def get_x_rotation(self, x, y, z):
        radians = math.atan2(y, self.dist(x, z))
        return math.degrees(radians)

    def GyroSensor(self):
        global step_flag
        global sum_step
        try:
            time.sleep(0.5)
            print("------------------------------------------------------")
            print("Gyro Sensor Data of X, Y, Z")
            gyro_xout = self.read_word_2c(0x43)
            gyro_yout = self.read_word_2c(0x45)
            gyro_zout = self.read_word_2c(0x47)

            print("gyro_xout: ", gyro_xout, " scaled: ", (gyro_xout / 131))
            print("gyro_yout: ", gyro_yout, " scaled: ", (gyro_yout / 131))
            print("gyro_zout: ", gyro_zout, " scaled: ", (gyro_zout / 131))

            print("Acceleration Data")
            accel_xout = self.read_word_2c(0x3b)
            accel_yout = self.read_word_2c(0x3d)
            accel_zout = self.read_word_2c(0x3f)

            accel_xout_scaled = accel_xout / 16384.0
            accel_yout_scaled = accel_yout / 16384.0
            accel_zout_scaled = accel_zout / 16384.0

            print("accel_xout: ", accel_xout, " scaled: ", accel_xout_scaled)
            print("accel_yout: ", accel_yout, " scaled: ", accel_yout_scaled)
            print("accel_zout: ", accel_zout, " scaled: ", accel_zout_scaled)
            print("x rotation: ", self.get_x_rotation(accel_xout_scaled, accel_yout_scaled, accel_zout_scaled))
            print("y rotation: ", self.get_y_rotation(accel_xout_scaled, accel_yout_scaled, accel_zout_scaled))

            if accel_xout > 0 and step_flag == 0:
                step_flag = 1

            elif accel_xout < 0 and step_flag == 1:
                step_flag = 0
                sum_step += 1

            # print("step_flag: %d" % step_flag)
            print("my working count : %d" % sum_step)
            print("------------------------------------------------------")

        except KeyboardInterrupt:
            GPIO.cleanup()

        threading.Timer(0.05, self.GyroSensor).start()


if __name__ == '__main__':
    global step_flag
    global sum_step
    step_flag = 0
    sum_step = 0

    GpsSensor()
    gyro = Gyro()
    gyro.GyroSensor()
    # SndData()
    fileHanding()
