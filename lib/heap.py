class Heap:

    def __init__(self):
        self.pq = [None]
        self.N = 0

    def remove(self):
        first = self.pq[1]
        self.pq[1] = self.pq[-1]
        self.sink(1)
        self.pq.pop(-1)
        self.N -=1
        return first

    def swim(self, key):
        pass

    def sink(self, key):
        pass

    def greater(self, i, j):
        return self.pq[i] > self.pq[j]

    def less(self, i, j):
        return self.pq[i] < self.pq[j]

    def exch(self, i, j):
        tmp = self.pq[i]
        self.pq[i] = self.pq[j]
        self.pq[j] = tmp

    def peek(self):
        if self.N == 0:
            return None
        else:
            return self.pq[1]

    def size(self):
        return self.N


class MaxHeap(Heap):

    def insert(self, key):
        self.pq.append(key)
        self.N += 1
        self.swim(self.N)

    def isMaxHeap(self):
        return True

    def swim(self, k):
        while k > 1 and self.less(k/2, k):
            self.exch(k/2, k)
            k = k/2

    def sink(self, k):
        while 2*k <= self.N:
            j = 2*k
            if j < self.N and self.less(j, j+1):
                j += 1
            if not self.less(k, j):
                break
            self.exch(k, j)
            k = j


class MinHeap(Heap):

    def insert(self, key):
        self.pq.append(key)
        self.N += 1
        self.swim(self.N)
        assert(self.isMinHeap())

    def swim(self, k):
        while k > 1 and self.greater(k/2, k):
            self.exch(k/2, k)
            k = k/2

    def sink(self, k):
        while 2*k <= self.N:
            j = 2*k
            if j < self.N and self.greater(j, j+1):
                j += 1
            if not self.greater(k, j):
                break
            self.exch(k, j)
            k = j

    def isMinHeap(self):
        return True