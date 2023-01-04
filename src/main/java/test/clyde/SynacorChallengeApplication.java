package test.clyde;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SynacorChallengeApplication {

    public static void main(String[] args) {
        try {
            new SynacorChallengeApplication().run();
        } catch (Throwable t) {
            t.printStackTrace();
        }
//		SpringApplication.run(SynacorChallengeApplication.class, args);
    }

    private boolean showReg = true;
    private boolean showStack = true;

    public void run() throws IOException {
        boolean solveCoins = false;
        if (solveCoins) {
            solveCoinCombination();
        }

        boolean calcTeleportReg7 = false;
        int teleportReg7;
        if (calcTeleportReg7) {
            teleportReg7 = calculateTeleport();
        } else {
            teleportReg7 = 25734;
        }

        boolean solveMaze = false;
        if (solveMaze) {
            solveMaze();
        }
        
        boolean finalCode = false;
        if (finalCode) {
            System.out.println(new StringBuilder("YqWlOuUuuOYo").reverse().toString().replace('q', 'p'));
            System.exit(0);
        }

        List<Integer> oProgramMemory = readFile("src/main/resources/challenge.bin");
        boolean allInventory = false;
        if (allInventory) {
            for (int i = 2670; i <= 2730; i += 4) {
                oProgramMemory.set(i, 0);
            }
        }
        List<Integer> programMemory = new ArrayList<>(oProgramMemory);

        Map<Integer, Integer> registers = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            registers.put(i, 0);
        }

        Stack<Integer> stack = new Stack<>();
        String readBuffer = "take tablet\nuse tablet\ndoorway\nnorth\nnorth\nbridge\ncontinue\ndown\neast\ntake empty lantern\nwest\nwest\npassage\nladder\nwest\nsouth\nnorth\ntake can\nuse can\nuse lantern\nwest\nladder\ndarkness\ncontinue\nwest\nwest\nwest\nwest\nnorth\ntake red coin\nnorth\neast\ntake concave coin\ndown\ntake corroded coin\nup\nwest\nwest\ntake blue coin\nup\ntake shiny coin\ndown\neast\nuse blue coin\nuse red coin\nuse shiny coin\nuse concave coin\nuse corroded coin\nnorth\ntake teleporter\nuse teleporter\ntake strange book\ntake business card\nuse teleporter\nwest\nnorth\nnorth\nnorth\nnorth\nnorth\nnorth\nnorth\nnorth\nnorth\ntake orb\nnorth\neast\neast\nnorth\nwest\nsouth\neast\neast\nwest\nnorth\nnorth\neast\nvault\ntake mirror\nuse mirror\n";
        String outputBuffer = "";

        int index = 484;
        boolean debug = false;
        boolean showReg = false;
        boolean addressTrack = false;
        boolean firstTeleport = true;
        int addressTrackIndex = 0;
        Map<Integer, Integer> addresses = new HashMap<>();
        List<Integer> addressTrace = new LinkedList<>();
        while (true) {
            if (showReg) {
                System.out.print('\r' + inlineReg(registers));
            }
            if (addressTrack) {
                addressTrace.add(index);
                addresses.putIfAbsent(index, 0);
                addresses.put(index, addresses.get(index) + 1);
                addressTrackIndex++;
                if (addressTrackIndex % 1000000 == 0) {
                    System.out.println();
                    addresses.entrySet().stream().sorted((e1, e2) -> e2.getValue() - e1.getValue()).limit(10)
                            .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
                    printMemory(index, programMemory, registers);
                }
            }
            if (index == 6027) {
                System.out.println();
            }
            int instruction = programMemory.get(index++);
            if (instruction == 0) {
                break;
            } else if (instruction == 1) {
                int a = getValue(programMemory.get(index++), registers, true);
                int b = getValue(programMemory.get(index++), registers);
                if (debug)
                    System.out.println("set " + a + " " + b);
                registers.put(a, b);
            } else if (instruction == 2) {
                int a = getValue(programMemory.get(index++), registers);
                stack.push(a);
                if (debug)
                    System.out.println("push " + a);
            } else if (instruction == 3) {
                int a = getValue(programMemory.get(index++), registers, true);
                int value = stack.pop();
                registers.put(a, value);
                if (debug)
                    System.out.println("pop " + a + " (" + value + ")");
            } else if (instruction == 4) {
                int a = getValue(programMemory.get(index++), registers, true);
                int b = getValue(programMemory.get(index++), registers);
                int c = getValue(programMemory.get(index++), registers);
                if (b == c) {
                    registers.put(a, 1);
                } else {
                    registers.put(a, 0);
                }
                if (debug)
                    System.out.println("eq " + a + " " + b + " " + c);
            } else if (instruction == 5) {
                int a = getValue(programMemory.get(index++), registers, true);
                int b = getValue(programMemory.get(index++), registers);
                int c = getValue(programMemory.get(index++), registers);
                if (b > c) {
                    registers.put(a, 1);
                } else {
                    registers.put(a, 0);
                }
                if (debug)
                    System.out.println("gt " + a + " " + b + " " + c);
            } else if (instruction == 6) {
                int jump = getValue(programMemory.get(index), registers);
                if (debug)
                    System.out.println("jmp " + jump);
                index = jump;
            } else if (instruction == 7) {
                int a = getValue(programMemory.get(index++), registers);
                int b = getValue(programMemory.get(index++), registers);
                if (a != 0)
                    index = b;
                if (debug)
                    System.out.println("jt " + a + " " + b);
            } else if (instruction == 8) {
                int a = getValue(programMemory.get(index++), registers);
                int b = getValue(programMemory.get(index++), registers);
                if (a == 0)
                    index = b;
                if (debug)
                    System.out.println("jf " + a + " " + b);
            } else if (instruction == 9) {
                int a = getValue(programMemory.get(index++), registers, true);
                int b = getValue(programMemory.get(index++), registers);
                int c = getValue(programMemory.get(index++), registers);
                registers.put(a, (b + c) % 32768);
                if (debug)
                    System.out.println("add " + a + " " + b + " " + c);
            } else if (instruction == 10) {
                int a = getValue(programMemory.get(index++), registers, true);
                int b = getValue(programMemory.get(index++), registers);
                int c = getValue(programMemory.get(index++), registers);
                registers.put(a, (b * c) % 32768);
                if (debug)
                    System.out.println("mult " + a + " " + b + " " + c);
            } else if (instruction == 11) {
                int a = getValue(programMemory.get(index++), registers, true);
                int b = getValue(programMemory.get(index++), registers);
                int c = getValue(programMemory.get(index++), registers);
                registers.put(a, b % c);
                if (debug)
                    System.out.println("mod " + a + " " + b + " " + c);
            } else if (instruction == 12) {
                int a = getValue(programMemory.get(index++), registers, true);
                int b = getValue(programMemory.get(index++), registers);
                int c = getValue(programMemory.get(index++), registers);
                registers.put(a, b & c);
                if (debug)
                    System.out.println("and " + a + " " + b + " " + c);
            } else if (instruction == 13) {
                int a = getValue(programMemory.get(index++), registers, true);
                int b = getValue(programMemory.get(index++), registers);
                int c = getValue(programMemory.get(index++), registers);
                registers.put(a, b | c);
                if (debug)
                    System.out.println("or " + a + " " + b + " " + c);
            } else if (instruction == 14) {
                int a = getValue(programMemory.get(index++), registers, true);
                int b = getValue(programMemory.get(index++), registers);
                registers.put(a, 32767 - b);
                if (debug)
                    System.out.println("not " + a + " " + b);
            } else if (instruction == 15) {
                int a = getValue(programMemory.get(index++), registers, true);
                int b = getValue(programMemory.get(index++), registers);
                registers.put(a, programMemory.get(b));
                if (debug)
                    System.out.println("rmem " + a + " " + b);
            } else if (instruction == 16) {
                int a = getValue(programMemory.get(index++), registers);
                int b = getValue(programMemory.get(index++), registers);
                programMemory.set(a, b);
//                    System.out.println("wmem " + a + " " + b);
                if (debug)
                    System.out.println("wmem " + a + " " + b);
            } else if (instruction == 17) {
                int a = getValue(programMemory.get(index++), registers);
                if (a == 6027) {
                    a = 5498;
//                    addressTrack = true;
                } else {
                    stack.push(index);
                }
                index = a;
                if (debug)
                    System.out.println("call " + a);
            } else if (instruction == 18) {
                index = stack.pop();
                if (debug)
                    System.out.println("ret");
            } else if (instruction == 19) {
                int display = getValue(programMemory.get(index++), registers);
                outputBuffer += (char) display;
//                    if (outputBuffer.contains("oGEFXdeMKXFD")) {
//                        reg8Index++;
//                        continue test;
//                    } else if (outputBuffer.contains("It begins to rain")) {
//                        System.out.println(outputBuffer.trim());
//                        System.out.println("reg8Index: " + reg8Index);
//                        System.exit(0);
//                    }
                if (!showReg) {
                    System.out.print((char) display);
                }
            } else if (instruction == 20) {
                int a = getValue(programMemory.get(index++), registers, true);

                if (readBuffer == null) {
                    System.out.println("> ");
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
                    readBuffer = buffer.readLine();
                    while (readBuffer.equals("debug") || readBuffer.equals("eval") || readBuffer.equals("showReg")
                            || readBuffer.equals("addressTrack") || readBuffer.equals("printMemory")) {
                        if (readBuffer.equals("debug")) {
                            debug = !debug;
                            System.out.println("debugger activated");
                        } else if (readBuffer.equals("eval")) {
                            registers.put(7, 2);
                            System.out.println("eval");
                        } else if (readBuffer.equals("showReg")) {
                            System.out.println("showReg");
                            showReg = !showReg;
                        } else if (readBuffer.equals("addressTrack")) {
                            System.out.println("addressTrack");
                            addressTrack = !addressTrack;
                        } else if (readBuffer.equals("printMemory")) {
                            printMemory(index, programMemory, registers);
                        }
                        readBuffer = buffer.readLine();
                    }
                    readBuffer += '\n';
                }
                if (readBuffer.startsWith("use teleporter")) {
                    if (firstTeleport) {
                        firstTeleport = false;
                    } else {
                        // addressTrack = true;
                        registers.put(7, teleportReg7);
                    }
                }
                char ch = readBuffer.charAt(0);
                registers.put(a, (int) ch);
                if (readBuffer.length() == 1) {
                    readBuffer = null;
                } else {
                    readBuffer = readBuffer.substring(1);
                }
            } else if (instruction == 21) {
                continue;
            } else {
                throw new RuntimeException("unhandled: " + instruction);
            }

        }
    }

    private void printReg(Map<Integer, Integer> registers) {
        if (showReg) {
            System.out.print("registers - [");
            for (int i = 0; i < 8; i++) {
                System.out.print(i + ": " + registers.get(i) + "  ");
            }
            System.out.println("");
        }
    }

    private String inlineReg(Map<Integer, Integer> registers) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(String.format("|%d: %5d ", i, registers.get(i)));
        }
        sb.append('|');
        return sb.toString();
    }

    private void printStack(Stack<Integer> stack) {
        if (showStack) {
            System.out.println("stack - " + stack.toString());
        }
    }

    private List<Integer> readFile(String fileName) throws IOException {
        FileInputStream fis = new FileInputStream("src/main/resources/challenge.bin");
        List<Integer> result = new ArrayList<>();
        while (true) {
            int read = getNext(fis);
            if (read == -1)
                break;
            result.add(read);
        }
        fis.close();
        return result;
    }

    private static int getNext(FileInputStream fis) throws IOException {
        int b1 = fis.read();
        if (b1 == -1)
            return -1;
        int b2 = fis.read();
        if (b2 == -1)
            return -1;
        int value = (b2 << 8) + b1;
        return value;
    }

    private int getValue(int val, Map<Integer, Integer> registers) {
        return getValue(val, registers, false);
    }

    private int getValue(int val, Map<Integer, Integer> registers, boolean forReg) {
        int result = val;
        if (val >= 32768 && val <= 32775) {
            if (forReg) {
                result = val - 32768;
            } else {
                result = registers.get((val - 32768));
            }
        } else if (val >= 32776) {
            throw new RuntimeException("invalid number: " + val);
        }
        return result % 32768;
    }

    private void printMemory(int address, List<Integer> programMemory, Map<Integer, Integer> registers) {
        int index = address;
        int count = 0;
        StringBuilder sb = new StringBuilder();
        while (count++ < 25) {

            int instruction = programMemory.get(index++);
            if (instruction == 0) {
                break;
            } else if (instruction == 1) {
                int aVal = programMemory.get(index++);
                int a = getValue(aVal, registers, true);
                int bVal = programMemory.get(index++);
                int b = getValue(bVal, registers);
                sb.append(String.format("%5d | set reg%d (%d) to %d (%d)", index - 3, a, aVal, b, bVal));
            } else if (instruction == 2) {
                int aVal = programMemory.get(index++);
                int a = getValue(aVal, registers);
                sb.append(String.format("%5d | push %d (%d)", index - 2, a, aVal));
            } else if (instruction == 3) {
                int aVal = programMemory.get(index++);
                int a = getValue(aVal, registers, true);
                sb.append(String.format("%5d | pop %d (%d)", index - 2, a, aVal));
            } else if (instruction == 4) {
                int aVal = programMemory.get(index++);
                int a = getValue(aVal, registers, true);
                int bVal = programMemory.get(index++);
                int b = getValue(bVal, registers);
                int cVal = programMemory.get(index++);
                int c = getValue(cVal, registers);
                sb.append(String.format("%5d | eq reg%d (%d) if %d (%d) == %d (%d)", index - 4, a, aVal, b, bVal, c,
                        cVal));
            } else if (instruction == 5) {
                int aVal = programMemory.get(index++);
                int a = getValue(aVal, registers, true);
                int bVal = programMemory.get(index++);
                int b = getValue(bVal, registers);
                int cVal = programMemory.get(index++);
                int c = getValue(cVal, registers);
                sb.append(
                        String.format("%5d | gt reg%d (%d) %d (%d) == %d (%d)", index - 4, a, aVal, b, bVal, c, cVal));
            } else if (instruction == 6) {
                int aVal = programMemory.get(index++);
                int jump = getValue(aVal, registers);
                sb.append(String.format("%5d | jmp %d (%d)", index - 2, jump, aVal));
            } else if (instruction == 7) {
                int aVal = programMemory.get(index++);
                int a = getValue(aVal, registers);
                int bVal = programMemory.get(index++);
                int b = getValue(bVal, registers);
                sb.append(String.format("%5d | jt if %d (%d) != 0 jump %d (%d)", index - 3, a, aVal, b, bVal));
            } else if (instruction == 8) {
                int aVal = programMemory.get(index++);
                int a = getValue(aVal, registers);
                int bVal = programMemory.get(index++);
                int b = getValue(bVal, registers);
                sb.append(String.format("%5d | jf if %d (%d) == 0 jump %d (%d)", index - 3, a, aVal, b, bVal));
            } else if (instruction == 9) {
                int aVal = programMemory.get(index++);
                int a = getValue(aVal, registers, true);
                int bVal = programMemory.get(index++);
                int b = getValue(bVal, registers);
                int cVal = programMemory.get(index++);
                int c = getValue(cVal, registers);
                sb.append(String.format("%5d | add into reg%d (%d) %d (%d) + %d (%d)", index - 4, a, aVal, b, bVal, c,
                        cVal));
            } else if (instruction == 10) {
                int aVal = programMemory.get(index++);
                int a = getValue(aVal, registers, true);
                int bVal = programMemory.get(index++);
                int b = getValue(bVal, registers);
                int cVal = programMemory.get(index++);
                int c = getValue(cVal, registers);
                sb.append(String.format("%5d | mult into reg%d (%d) %d (%d) * %d (%d)", index - 4, a, aVal, b, bVal, c,
                        cVal));
            } else if (instruction == 11) {
                int aVal = programMemory.get(index++);
                int a = getValue(aVal, registers, true);
                int bVal = programMemory.get(index++);
                int b = getValue(bVal, registers);
                int cVal = programMemory.get(index++);
                int c = getValue(cVal, registers);
                sb.append(String.format("%5d | mod into reg%d (%d) %d (%d) %% %d (%d)", index - 4, a, aVal, b, bVal, c,
                        cVal));
            } else if (instruction == 12) {
                int aVal = programMemory.get(index++);
                int a = getValue(aVal, registers, true);
                int bVal = programMemory.get(index++);
                int b = getValue(bVal, registers);
                int cVal = programMemory.get(index++);
                int c = getValue(cVal, registers);
                sb.append(String.format("%5d | and into reg%d (%d) %d (%d) & %d (%d)", index - 4, a, aVal, b, bVal, c,
                        cVal));
            } else if (instruction == 13) {
                int aVal = programMemory.get(index++);
                int a = getValue(aVal, registers, true);
                int bVal = programMemory.get(index++);
                int b = getValue(bVal, registers);
                int cVal = programMemory.get(index++);
                int c = getValue(cVal, registers);
                sb.append(String.format("%5d | or into reg%d (%d) %d (%d) | %d (%d)", index - 4, a, aVal, b, bVal, c,
                        cVal));
            } else if (instruction == 14) {
                int aVal = programMemory.get(index++);
                int a = getValue(aVal, registers, true);
                int bVal = programMemory.get(index++);
                int b = getValue(bVal, registers);
                sb.append(String.format("%5d | not into reg%d (%d) !%d (%d)", index - 3, a, aVal, b, bVal));
            } else if (instruction == 15) {
                int aVal = programMemory.get(index++);
                int a = getValue(aVal, registers, true);
                int bVal = programMemory.get(index++);
                int b = getValue(bVal, registers);
                sb.append(String.format("%5d | rmem read memory into reg %d (%d) address %d (%d)", index - 3, a, aVal,
                        b, bVal));
            } else if (instruction == 16) {
                int aVal = programMemory.get(index++);
                int a = getValue(aVal, registers);
                int bVal = programMemory.get(index++);
                int b = getValue(bVal, registers);
                sb.append(String.format("%5d | wmem write memory address %d (%d) value %d (%d)", index - 3, a, aVal, b,
                        bVal));
            } else if (instruction == 17) {
                int aVal = programMemory.get(index++);
                int a = getValue(aVal, registers);
                sb.append(String.format("%5d | call address %d (%d)", index - 2, a, aVal));
            } else if (instruction == 18) {
                sb.append(String.format("%5d | ret", index - 1));
            } else if (instruction == 19) {
                int aVal = programMemory.get(index++);
                int display = getValue(aVal, registers);
                sb.append(String.format("%5d | out %d (%d)", index - 2, display, aVal));
            } else if (instruction == 20) {
                int aVal = programMemory.get(index++);
                int a = getValue(aVal, registers, true);
                sb.append(String.format("%5d | in reg%d (%d)", index - 2, a, aVal));
            } else if (instruction == 21) {
                sb.append(String.format("%5d | noop", index - 1));
            }
            sb.append('\n');
        }
        System.out.println(sb.toString());
    }

    private void solveCoinCombination() {
        Integer[] numbers = new Integer[] { 2, 7, 3, 9, 5 };
        Permutations<Integer> perm = new Permutations<>(Arrays.asList(numbers));
        for (List<Integer> combination : perm) {
            double result = combination.get(0) + combination.get(1) * Math.pow(combination.get(2), 2)
                    + Math.pow(combination.get(3), 3) - combination.get(4);
            if (result == 399.0) {
                System.out.println("solution: " + combination);
            }
        }
    }

    private PairInt f6027(int reg0, int reg1, int reg7, Map<PairInt, PairInt> memoization) {
        PairInt key = new PairInt(reg0, reg1);
        if (memoization.containsKey(key)) {
            return memoization.get(key);
        }

        if (reg0 == 0) {
            reg0 = (reg1 + 1) % 32768;
            PairInt result = new PairInt(reg0, reg1);
            memoization.put(key, result);
            return result;
        }

        if (reg1 == 0) {
            reg0 = (reg0 + 32767) % 32768; // reg0--
            reg1 = reg7;
            PairInt result = f6027(reg0, reg1, reg7, memoization);
            memoization.put(key, result);
            return result;
        }

        int store = reg0;
        reg1 = (reg1 + 32767) % 32768; // reg1--
        PairInt result = f6027(reg0, reg1, reg7, memoization);
        reg1 = result.getLeft();
        reg0 = (store + 32767) % 32768; // reg0--
        result = f6027(reg0, reg1, reg7, memoization);
        memoization.put(key, result);
        return result;
    }

    private int calculateTeleport() {
        Integer reg0 = Integer.valueOf(4);
        Integer reg1 = Integer.valueOf(1);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        List<Future<Optional<Integer>>> futures = new ArrayList<>();
        for (int i = 0; i < 32768; i++) {
            final int reg7Index = i;
            futures.add(executor.submit(() -> {
                Integer reg7 = Integer.valueOf(reg7Index);
                Map<PairInt, PairInt> memoization = new HashMap<>();
                PairInt result = f6027(reg0, reg1, reg7, memoization);
                if (result.getLeft() == 6) {
                    return Optional.of(reg7Index);
                }
                System.out.println(reg7Index + ": " + result);
                return Optional.empty();
            }));
        }
        return futures.stream().map(f -> {
            try {
                return f.get();
            } catch (Exception e) {
                e.printStackTrace();
                Optional<Integer> result = Optional.empty();
                return result;
            }
        }).filter(o -> o.isPresent()).map(o -> o.get()).findFirst().get();
    }

    private class Move implements Comparable<Move> {
        PairInt location;
        int currentAmount;
        String previousTile;
        List<Direction> previousMoves = new ArrayList<>();

        public Move(PairInt location, int currentAmount, String previousTile) {
            super();
            this.location = location;
            this.currentAmount = currentAmount;
            this.previousTile = previousTile;
        }

        @Override
        public int compareTo(Move o) {
            return previousMoves.size() - o.previousMoves.size();
        }
    }

    private void solveMaze() {
        String[][] grid = new String[][] { { "*", "8", "-", "1" }, { "4", "*", "11", "*" }, { "+", "4", "-", "18" }, { "22", "-", "9", "*" } };
        Move start = new Move(new PairInt(0, 3), 22, null);
        int score = 30;

        Queue<Move> moves = new PriorityQueue<>();
        moves.add(start);
        while (moves.size() > 0) {
            Move currentMove = moves.remove();
            if (currentMove.currentAmount == score && currentMove.location.getLeft() == 3
                    && currentMove.location.getRight() == 0) {
                for (int i=0; i<currentMove.previousMoves.size(); i++) {
                    Direction direction = currentMove.previousMoves.get(i);
                    System.out.println(direction);
                }
                break;
            }
            for (Direction direction : Direction.values()) {
                int nextX = currentMove.location.getLeft() + direction.x;
                if (nextX < 0 || nextX > 3)
                    continue;
                int nextY = currentMove.location.getRight() + direction.y;
                if (nextY < 0 || nextY > 3)
                    continue;
                if (nextX == 0 && nextY == 3) continue;
                int nextAmount = currentMove.currentAmount;
                String storeTile;
                if (grid[nextY][nextX].equals("*") || grid[nextY][nextX].equals("+")
                        || grid[nextY][nextX].equals("-")) {
                    storeTile = grid[nextY][nextX];
                } else {
                    int gridValue = Integer.valueOf(grid[nextY][nextX]);
                    if (currentMove.previousTile == null) {
                        nextAmount = gridValue;
                    } else {
                        if (currentMove.previousTile.equals("*")) {
                            nextAmount *= gridValue;
                        } else if (currentMove.previousTile.equals("-")) {
                            nextAmount -= gridValue;
                        } else if (currentMove.previousTile.equals("+")) {
                            nextAmount += gridValue;
                        }
                    }
                    storeTile = null;
                }
                Move nextMove = new Move(new PairInt(nextX, nextY), nextAmount, storeTile);
                nextMove.previousMoves.addAll(currentMove.previousMoves);
                nextMove.previousMoves.add(direction);
                moves.add(nextMove);
            }

        }
    }
}
