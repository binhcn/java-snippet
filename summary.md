| ID | Client A     | Server B              | Server C   |
|----|:------------:|:---------------------:|:----------:|
| 1  | deadline(2s) | calling               | sleep(5s)  |
| 2  | deadline(2s) | sleep(5s) before call | x          |
| 3  | deadline(2s) | sleep(5s) after call  | x          |
| 4  | x            | calling               | sleep(5s)  |
| 5  | x            | sleep(5s) before call | x          |
| 6  | x            | sleep(5s) after call  | x          |
| 7  | x            | deadline(2s)          | sleep(5s)  |
| 8  | deadline(1s) | deadline(2s)          | sleep(5s)  |
| 9  | deadline(2s) | deadline(1s)          | sleep(5s)  |

### ID = 1
- client A: code=DEADLINE_EXCEEDED (2s)
- server B: CANCELLED: io.grpc.Context was cancelled without error (2s)

### ID = 2
- client A: code=DEADLINE_EXCEEDED (2s)
- server B: DEADLINE_EXCEEDED: ClientCall started after deadline exceeded (5s)
- server C: not receive request from B

### ID = 3
- client A: code=DEADLINE_EXCEEDED (2s)
- server B: x

### ID = 4, 5, 6
- client A: x
- server B: x

### ID = 7
- client A: Status{code=UNKNOWN, description=null, cause=null} (2s_)
- server B: DEADLINE_EXCEEDED: deadline exceeded after... (2s)

### ID = 8
- client A: Status{code=DEADLINE_EXCEEDED, description=deadline exceeded after...} (1s_)
- server B: CANCELLED: io.grpc.Context was cancelled without error (1s)

### ID = 9
- client A: Status{code=UNKNOWN, description=null, cause=null} (1s_)
- server B: DEADLINE_EXCEEDED: deadline exceeded after... (1s)