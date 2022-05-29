Imports System.IO.Ports

Public Class Form1
    Private picture As Image
    Private angle As Double
    Private new_angle As Double
    Private pastilla_tomada As Boolean
    Private pastilla_detectada As Boolean

    Private buzzer_time As Integer
    Private led_time As Integer

    Private lectura As String

    Public Function RotateImage(ByRef image As Image, ByVal offset As PointF, ByVal angle As Decimal) As Bitmap
        If image Is Nothing Then
            Throw New ArgumentNullException("image")
        End If
        ''create a new empty bitmap to hold rotated image
        'Dim rotatedBmp As Bitmap = New Bitmap(image.Width, image.Height)
        Dim rotatedBmp As Bitmap = New Bitmap(500, 500)
        'Dim rotatedBmp As Bitmap = New Bitmap(image)
        rotatedBmp.SetResolution(image.HorizontalResolution, image.VerticalResolution)
        ''make a graphics object from the empty bitmap
        Dim g As Graphics = Graphics.FromImage(rotatedBmp)
        ''Put the rotation point in the center of the image
        g.TranslateTransform(offset.X, offset.Y)
        ''rotate the image
        g.RotateTransform(angle)
        ''move the image back
        ''g.TranslateTransform(-offset.X, -offset.Y)
        ''draw passed in image onto graphics object
        g.DrawImage(image, New PointF(-250, -250))
        'g.DrawImage(image, offset)
        Return rotatedBmp
    End Function


    Private Sub Form1_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        angle = 22.5
        new_angle = 22.5
        picture = PictureBox1.Image
        Timer1.Enabled = True

        'PictureBox2.BackColor = Color.Transparent

        SerialPort1.PortName = "COM6"
        SerialPort1.Open()

        pastilla_tomada = True
        pastilla_detectada = True
        buzzer_time = 0
        led_time = 0
        lectura = ""

        PictureBox1.Image = RotateImage(picture, New PointF(250, 250), angle)
    End Sub

    Private Sub Timer1_Tick(sender As Object, e As EventArgs) Handles Timer1.Tick
        If Not new_angle = angle Then
            angle = angle + 0.5
            PictureBox1.Image = RotateImage(picture, New PointF(250, 250), angle)

            If angle >= new_angle Then
                picArrow.Visible = True
            Else
                picArrow.Visible = False
            End If
        End If

        If buzzer_time >= 0 Then
            buzzer_time -= Timer1.Interval
        Else
            picSpeaker.Visible = False
        End If

        If led_time >= 0 Then
            led_time -= Timer1.Interval
        Else
            picRedLight.Visible = False
        End If

        If Not lectura = "" Then
            'decode()
        End If


        'Rotate the image by a further 1 degree.
        'Me.angle = (Me.angle + 1) Mod 360

        'Repaint the image.
        Me.PictureBox1.Refresh()
    End Sub

    Private Sub decode()
        If lectura.Contains("<") Then
            If lectura.Length > 2 Then
                If lectura.Substring(1, 1) = "M" Then
                    'motor (write)
                    new_angle += Convert.ToInt32(lectura.Substring(2, 2))
                ElseIf lectura.Substring(1, 1) = "B" Then
                    'buzzer (write)
                    picSpeaker.Visible = True
                    buzzer_time = lectura.Substring(2, 4)
                ElseIf lectura.Substring(1, 1) = "L" Then
                    'led (write)
                    picRedLight.Visible = True
                    led_time = lectura.Substring(2, 4)
                ElseIf lectura.Substring(1, 1) = "T" Then
                    'consulta pastillas tomadas (read)
                    If btnTake.Enabled = true Then
                        SerialPort1.Write("1")
                    Else
                        SerialPort1.Write("0")
                    End If
                ElseIf lectura.Substring(1, 1) = "C" Then
                    'consulta pastilla en cubeta (read)
                    If picArrow.Visible = False Then
                        SerialPort1.Write("1")
                    Else
                        SerialPort1.Write("0")
                    End If
                End If
            End If
        End If

        lectura = ""
    End Sub

    Private Sub picArrow_Click(sender As Object, e As EventArgs) Handles picArrow.Click
        picArrow.Visible = False
        btnTake.Enabled = True
        pastilla_detectada = True

    End Sub

    Private Sub btnTake_Click(sender As Object, e As EventArgs) Handles btnTake.Click
        btnTake.Enabled = False
        pastilla_tomada = True

    End Sub

    Private Sub SerialPort1_DataReceived(sender As Object, e As SerialDataReceivedEventArgs) Handles SerialPort1.DataReceived
        CheckForIllegalCrossThreadCalls = False

        lectura = SerialPort1.ReadLine()
        Me.Text = lectura
        decode()
    End Sub
End Class
