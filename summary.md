| module.enabled | none | true | false | 123 |
|----------------|------|:----:|:-----:|:---:|
| ID = 1         | 0    | 1    | 0     | 1   |
| ID = 2         | 1    | 1    | 0     | 1   |
| ID = 3         | 0    | 1    | 0     | 0   |
| ID = 4         | 1    | 1    | 0     | 0   |
| ID = 5         | 0    | 0    | 1     | 0   |
| ID = 6         | 1    | 0    | 1     | 0   |

* `matchIfMissing = false` is equivalent to `none`

### ID = 1
- @ConditionalOnProperty(prefix = "module", name = "enabled")

### ID = 2
- @ConditionalOnProperty(prefix = "module", name = "enabled", matchIfMissing = true)

### ID = 3
- 	@ConditionalOnProperty(prefix = "module", name = "enabled", havingValue = "true")

### ID = 4
- 	@ConditionalOnProperty(prefix = "module", name = "enabled", matchIfMissing = true, havingValue = "true")

### ID = 5
- 	@ConditionalOnProperty(prefix = "module", name = "enabled", havingValue = "false")

### ID = 6
- 	@ConditionalOnProperty(prefix = "module", name = "enabled", matchIfMissing = true, havingValue = "false")

