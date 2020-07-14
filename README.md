主要功能：Android崩溃日志记录，并通过Javamail邮件发送到收件人。

注意： （1）修改发件人和授权码 （2）修改收件人。

如果发送的是qq企业邮箱，那么在mail.setUserName(split[0]); // 你的邮箱地址。需要将名字修改为发送人的完整邮箱地址。

移植功能时只需看MainActivity就行，移植的文件只需util里的 mail 文件夹就行，然后加上 libs包里的jar包，其他不用管