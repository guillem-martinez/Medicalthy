<Global.Microsoft.VisualBasic.CompilerServices.DesignerGenerated()>
Partial Class Form1
    Inherits System.Windows.Forms.Form

    'Form reemplaza a Dispose para limpiar la lista de componentes.
    <System.Diagnostics.DebuggerNonUserCode()>
    Protected Overrides Sub Dispose(ByVal disposing As Boolean)
        Try
            If disposing AndAlso components IsNot Nothing Then
                components.Dispose()
            End If
        Finally
            MyBase.Dispose(disposing)
        End Try
    End Sub

    'Requerido por el Diseñador de Windows Forms
    Private components As System.ComponentModel.IContainer

    'NOTA: el Diseñador de Windows Forms necesita el siguiente procedimiento
    'Se puede modificar usando el Diseñador de Windows Forms.  
    'No lo modifique con el editor de código.
    <System.Diagnostics.DebuggerStepThrough()>
    Private Sub InitializeComponent()
        Me.components = New System.ComponentModel.Container()
        Me.Timer1 = New System.Windows.Forms.Timer(Me.components)
        Me.btnTake = New System.Windows.Forms.Button()
        Me.SerialPort1 = New System.IO.Ports.SerialPort(Me.components)
        Me.picRedLight = New System.Windows.Forms.PictureBox()
        Me.picSpeaker = New System.Windows.Forms.PictureBox()
        Me.picArrow = New System.Windows.Forms.PictureBox()
        Me.PictureBox1 = New System.Windows.Forms.PictureBox()
        CType(Me.picRedLight, System.ComponentModel.ISupportInitialize).BeginInit()
        CType(Me.picSpeaker, System.ComponentModel.ISupportInitialize).BeginInit()
        CType(Me.picArrow, System.ComponentModel.ISupportInitialize).BeginInit()
        CType(Me.PictureBox1, System.ComponentModel.ISupportInitialize).BeginInit()
        Me.SuspendLayout()
        '
        'Timer1
        '
        Me.Timer1.Interval = 12
        '
        'btnTake
        '
        Me.btnTake.Enabled = False
        Me.btnTake.Font = New System.Drawing.Font("Microsoft Sans Serif", 20.0!)
        Me.btnTake.Location = New System.Drawing.Point(12, 518)
        Me.btnTake.Name = "btnTake"
        Me.btnTake.Size = New System.Drawing.Size(500, 50)
        Me.btnTake.TabIndex = 3
        Me.btnTake.Text = "Tomar pastilla"
        Me.btnTake.UseVisualStyleBackColor = True
        '
        'SerialPort1
        '
        Me.SerialPort1.BaudRate = 115200
        '
        'picRedLight
        '
        Me.picRedLight.BackColor = System.Drawing.SystemColors.Control
        Me.picRedLight.Image = Global.InterfazPastillero_VB.My.Resources.Resources.luz_roja
        Me.picRedLight.Location = New System.Drawing.Point(422, 12)
        Me.picRedLight.Name = "picRedLight"
        Me.picRedLight.Size = New System.Drawing.Size(90, 90)
        Me.picRedLight.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage
        Me.picRedLight.TabIndex = 7
        Me.picRedLight.TabStop = False
        '
        'picSpeaker
        '
        Me.picSpeaker.BackColor = System.Drawing.SystemColors.Control
        Me.picSpeaker.Image = Global.InterfazPastillero_VB.My.Resources.Resources.altavoz
        Me.picSpeaker.Location = New System.Drawing.Point(12, 12)
        Me.picSpeaker.Name = "picSpeaker"
        Me.picSpeaker.Size = New System.Drawing.Size(90, 90)
        Me.picSpeaker.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage
        Me.picSpeaker.TabIndex = 6
        Me.picSpeaker.TabStop = False
        '
        'picArrow
        '
        Me.picArrow.BackColor = System.Drawing.Color.White
        Me.picArrow.Image = Global.InterfazPastillero_VB.My.Resources.Resources.flecha
        Me.picArrow.Location = New System.Drawing.Point(227, 362)
        Me.picArrow.Name = "picArrow"
        Me.picArrow.Size = New System.Drawing.Size(69, 84)
        Me.picArrow.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage
        Me.picArrow.TabIndex = 5
        Me.picArrow.TabStop = False
        Me.picArrow.Visible = False
        '
        'PictureBox1
        '
        Me.PictureBox1.Image = Global.InterfazPastillero_VB.My.Resources.Resources.rueda_pastillero
        Me.PictureBox1.Location = New System.Drawing.Point(12, 12)
        Me.PictureBox1.Name = "PictureBox1"
        Me.PictureBox1.Size = New System.Drawing.Size(500, 500)
        Me.PictureBox1.TabIndex = 0
        Me.PictureBox1.TabStop = False
        '
        'Form1
        '
        Me.AutoScaleDimensions = New System.Drawing.SizeF(6.0!, 13.0!)
        Me.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font
        Me.ClientSize = New System.Drawing.Size(524, 580)
        Me.Controls.Add(Me.picRedLight)
        Me.Controls.Add(Me.picSpeaker)
        Me.Controls.Add(Me.picArrow)
        Me.Controls.Add(Me.btnTake)
        Me.Controls.Add(Me.PictureBox1)
        Me.Name = "Form1"
        Me.Text = "Form1"
        CType(Me.picRedLight, System.ComponentModel.ISupportInitialize).EndInit()
        CType(Me.picSpeaker, System.ComponentModel.ISupportInitialize).EndInit()
        CType(Me.picArrow, System.ComponentModel.ISupportInitialize).EndInit()
        CType(Me.PictureBox1, System.ComponentModel.ISupportInitialize).EndInit()
        Me.ResumeLayout(False)

    End Sub

    Friend WithEvents PictureBox1 As PictureBox
    Friend WithEvents Timer1 As Timer
    Friend WithEvents btnTake As Button
    Friend WithEvents SerialPort1 As IO.Ports.SerialPort
    Friend WithEvents picArrow As PictureBox
    Friend WithEvents picSpeaker As PictureBox
    Friend WithEvents picRedLight As PictureBox
End Class
