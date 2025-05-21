const tagColors = [
  'bg-red-200',     // #fecaca
  'bg-orange-200',  // #fed7aa
  'bg-amber-200',   // #fde68a
  'bg-yellow-200',  // #fef08a
  'bg-lime-200',    // #d9f99d
  'bg-green-200',   // #bbf7d0
  'bg-emerald-200', // #a7f3d0
  'bg-teal-200',    // #99f6e4
  'bg-cyan-200',    // #a5f3fc
  'bg-sky-200',     // #bae6fd
  'bg-blue-200',    // #bfdbfe
  'bg-indigo-200',  // #c7d2fe
  'bg-violet-200',  // #ddd6fe
  'bg-purple-200',  // #e9d5ff
  'bg-pink-200',    // #fbcfe8
  'bg-rose-200',    // #fecdd3
]

export function getHashColor(tag: string): string {
  let hash = 0
  for (let i = 0; i < tag.length; i++) {
    hash = tag.charCodeAt(i) + ((hash << 5) - hash)
  }
  const index = Math.abs(hash) % tagColors.length
  return tagColors[index]
}

