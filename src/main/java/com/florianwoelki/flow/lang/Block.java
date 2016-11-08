package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.exception.InvalidCodeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Florian Woelki on 08.11.16.
 */
public abstract class Block
{
    private final Block superBlock;
    final List<Block> subBlocks;
    private final List<String> lines;

    public Block(Block superBlock)
    {
        this.superBlock = superBlock;
        this.subBlocks = new ArrayList<>();
        this.lines = new ArrayList<>();
    }

    protected abstract void runAfterParse() throws InvalidCodeException;

    public void addLine(String line)
    {
        this.lines.add(line);
    }

    public final void doBlocks() throws InvalidCodeException
    {
        for (Block block : this.subBlocks)
        {
            block.run();
        }
    }

    public Block[] getBlockTree()
    {
        List<Block> tree = new ArrayList<>();

        Block b = this;

        while (b != null)
        {
            tree.add(b);
            b = b.getSuperBlock();
        }

        Collections.reverse(tree);

        return tree.toArray(new Block[tree.size()]);
    }

    public void run() throws InvalidCodeException
    {
        Block currentBlock = null;

        lineLoop:
        for (String line : this.lines)
        {
            if (currentBlock == null)
            {
                for (ConditionalBlock.ConditionalBlockType bt : ConditionalBlock.ConditionalBlockType.values())
                {
                    if (line.startsWith(bt.name().toLowerCase()))
                    {
                        String[] args = Arrays.copyOfRange(line.split(" "), 1, line.split(" ").length);

                        if (bt == ConditionalBlock.ConditionalBlockType.IF)
                        {
                            currentBlock = new If(this, args[1], args[2], ConditionalBlock.CompareOperation.match(args[0]));
                        }
                        else if (bt == ConditionalBlock.ConditionalBlockType.WHILE)
                        {
                            currentBlock = new While(this, args[1], args[2], ConditionalBlock.CompareOperation.match(args[0]));
                        }

                        continue lineLoop;
                    }
                }
            }

            if (line.equals("end"))
            {
                if (currentBlock != null)
                {
                    currentBlock.addLine("end");
                    this.subBlocks.add(currentBlock);
                    currentBlock = null;
                }
                else
                {
                    break;
                }
            }
            else
            {
                if (currentBlock != null)
                {
                    currentBlock.addLine(line);
                }
                else
                {
                    this.subBlocks.add(new Line(this, line));
                }
            }
        }

        this.runAfterParse();
    }

    public Block getSuperBlock()
    {
        return this.superBlock;
    }
}